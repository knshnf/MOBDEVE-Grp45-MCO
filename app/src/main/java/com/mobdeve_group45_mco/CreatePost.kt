package com.mobdeve_group45_mco

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve_group45_mco.databinding.ActivityCreatePostBinding // Import the generated binding class
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

data class Post(
    val post: String = "",
    val date: String = "",
    var name: String = "",
    val city: String = "",
    val countryCode: String = ""
)

class CreatePost : AppCompatActivity()  {
    private lateinit var binding: ActivityCreatePostBinding // Declare the binding variable
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val city = intent.getStringExtra("CITY")
        val countryCode = intent.getStringExtra("COUNTRY_CODE")

        binding.btnSubmitPost.setOnClickListener {
            val postContent = binding.etPostContent.text.toString().trim()
            if (postContent.isNotEmpty()) {
                if (countryCode != null && city != null) {
                    uploadPost(postContent, city, countryCode)
                } else {
                    Toast.makeText(this, "City and/or country code is missing", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please write something!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun uploadPost(postContent: String, city: String, countryCode: String) {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = currentDate.format(formatter)
        val currentUser = auth.currentUser ?: return
        val postId = UUID.randomUUID().toString()

        db.collection("users")
            .document(currentUser.uid)
            .get()
            .addOnSuccessListener { userDocument ->
                if (userDocument.exists()) {
                    val post = Post(
                        post = postContent,
                        date = formattedDate,
                        name = userDocument.getString("name") ?: "Unknown",
                        city = city,
                        countryCode = countryCode
                    )
                    db.collection("posts")
                        .document(postId)
                        .set(post)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Post submitted successfully!", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Failed to submit post: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Log.e("FirestoreError", "User document not found for uid: $currentUser")
                }
            }
            .addOnFailureListener {
                Log.e("FirestoreError", "Error fetching posts: $currentUser")
            }
    }
}