package com.mobdeve_group45_mco

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve_group45_mco.databinding.ActivityEditProfileBinding

data class UserProfile(
    val name: String = "",
    val bio: String = ""
)

class EditProfile : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore


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

        val currentUser = auth.currentUser

        currentUser?.let { user ->
            db.collection("users").document(user.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        // Database exists, load data from it
                        val name = document.getString("name") ?: user.displayName ?: ""
                        val bio = document.getString("bio") ?: ""

                        // Set fields with existing data
                        binding.editProfileEtName.setText(name)
                        binding.editProfileEtBio.setText(bio)
                    } else {
                        // Database doesn't exist, use data from Firebase Auth
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

        val userProfile = UserProfile(
            name = newName,
            bio = newBio
        )

        // Check if user document exists before saving
        db.collection("users").document(currentUser.uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Update existing document
                    db.collection("users")
                        .document(currentUser.uid)
                        .update(mapOf(
                            "name" to newName,
                            "bio" to newBio
                        ))
                        .addOnSuccessListener {
                            updateAuthProfile(currentUser, newName)
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error updating profile: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    // Create new document
                    db.collection("users")
                        .document(currentUser.uid)
                        .set(userProfile)
                        .addOnSuccessListener {
                            updateAuthProfile(currentUser, newName)
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error creating profile: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error checking profile: ${e.message}", Toast.LENGTH_LONG).show()
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