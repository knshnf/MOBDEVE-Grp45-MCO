package com.mobdeve_group45_mco

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mobdeve_group45_mco.databinding.ActivityCreatePostBinding // Import the generated binding class
import java.util.*

class CreatePost : AppCompatActivity()  {
    private lateinit var binding: ActivityCreatePostBinding // Declare the binding variable
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle post submission
        binding.btnSubmitPost.setOnClickListener {
            val postContent = binding.etPostContent.text.toString().trim()
            if (postContent.isNotEmpty()) {
                uploadPostToFirebase(postContent)
            } else {
                Toast.makeText(this, "Please write something!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun uploadPostToFirebase(content: String) {
        val firestore = FirebaseFirestore.getInstance()
        val storage = FirebaseStorage.getInstance()
        val postId = UUID.randomUUID().toString()

        if (selectedImageUri != null) {
            val imageRef = storage.reference.child("posts/$postId.jpg")
            imageRef.putFile(selectedImageUri!!)
                .addOnSuccessListener { taskSnapshot ->
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        savePostToFirestore(postId, content, uri.toString())
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to upload image: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            savePostToFirestore(postId, content, null)
        }
    }

    private fun savePostToFirestore(postId: String, content: String, imageUrl: String?) {
        val firestore = FirebaseFirestore.getInstance()
        val postMap = hashMapOf(
            "postId" to postId,
            "content" to content,
            "imageUrl" to imageUrl,
            "city" to "Manila", // Replace with dynamic city data
            "countryCode" to "PH", // Replace with dynamic country code
            "userId" to "user123", // Replace with actual user ID from authentication
            "date" to System.currentTimeMillis()
        )

        firestore.collection("posts").document(postId).set(postMap)
            .addOnSuccessListener {
                Toast.makeText(this, "Post submitted successfully!", Toast.LENGTH_SHORT).show()
                finish() // Close the activity after submission
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to submit post: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}