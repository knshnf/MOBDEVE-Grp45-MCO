package com.mobdeve_group45_mco

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mobdeve_group45_mco.databinding.ActivityEditProfileBinding

data class UserProfile(
    val name: String = "",
    val bio: String = "",
    val profile_pic: String = ""
)

class EditProfile : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var storage: FirebaseStorage


    // Launcher for picking an image from the gallery
    private var selectedImageUri: Uri? = null
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val imageUri = data?.data
            if (imageUri != null) {
                selectedImageUri = imageUri
                binding.editProfileImgPicture.setImageURI(imageUri)
            } else {
                Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Image selection canceled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        val currentUser = auth.currentUser

        currentUser?.let { user ->
            db.collection("users").document(user.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        // Load data from Firestore
                        val name = document.getString("name") ?: user.displayName ?: ""
                        val bio = document.getString("bio") ?: ""
                        val profilePicUrl = document.getString("profile_pic")

                        binding.editProfileEtName.setText(name)
                        binding.editProfileEtBio.setText(bio)
                        profilePicUrl?.let {
                            loadImageFromFirebaseStorage(it)
                        }
                    } else {
                        // Use Firebase Auth data if Firestore data doesn't exist
                        binding.editProfileEtName.setText(user.displayName)
                        binding.editProfileEtBio.setText("")
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error loading profile: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

        binding.editProfileBtnSave.setOnClickListener {
            saveUserProfile()
        }

        binding.editProfileBtnUploadPicture.setOnClickListener {
            pickImageFromGallery()
            Toast.makeText(this, "Picture upload functionality coming soon", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadImageFromFirebaseStorage(imagePath: String) {
        val storageRef = storage.reference.child(imagePath)

        // Get the download URL and load it using Glide
        storageRef.downloadUrl.addOnSuccessListener { uri ->
            // Using Glide to load the image into ImageView

            Glide.with(this)
                .load(uri) // URI of the image
                .into(binding.editProfileImgPicture) // ImageView to set the image in
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Failed to load image: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }

    private fun saveUserProfile() {
        val currentUser = auth.currentUser ?: return
        val newName = binding.editProfileEtName.text.toString().trim()
        val newBio = binding.editProfileEtBio.text.toString().trim()

        if (newName.isEmpty()) {
            binding.editProfileEtName.error = "Name cannot be empty"
            return
        }

        if (selectedImageUri != null) {
            // Upload the image to Firebase Storage
            val storageRef = storage.reference.child("profile_pictures/${currentUser.uid}.jpg")
            storageRef.putFile(selectedImageUri!!)
                .addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        saveProfileToFirestore(currentUser, newName, newBio, "profile_pictures/${currentUser.uid}.jpg")
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error uploading image: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            // Save profile without a new image
            saveProfileToFirestore(currentUser, newName, newBio, null)
        }
    }

    private fun saveProfileToFirestore(currentUser: FirebaseUser, newName: String, newBio: String, profilePicUrl: String?) {
        val userProfile = UserProfile(
            name = newName,
            bio = newBio,
            profile_pic = profilePicUrl ?: ""
        )

        db.collection("users").document(currentUser.uid)
            .set(userProfile)
            .addOnSuccessListener {
                updateAuthProfile(currentUser, newName)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error saving profile: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateAuthProfile(currentUser: FirebaseUser, newName: String) {
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(newName)
            .build()

        currentUser.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Failed to update display name", Toast.LENGTH_SHORT).show()
                }
            }
    }
}