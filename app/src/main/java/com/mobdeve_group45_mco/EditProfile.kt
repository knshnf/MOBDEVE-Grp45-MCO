package com.mobdeve_group45_mco

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class EditProfile : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        auth = FirebaseAuth.getInstance()

        val imgProfile = findViewById<ImageView>(R.id.edit_profile_img_picture)
        val etName = findViewById<EditText>(R.id.edit_profile_et_name)
        val etBio = findViewById<EditText>(R.id.edit_profile_et_bio)
        val btnUpload = findViewById<Button>(R.id.edit_profile_btn_upload_picture)
        val btnSave = findViewById<Button>(R.id.edit_profile_btn_save)

        val currentUser = auth.currentUser
        etName.setText(currentUser?.displayName)
        etBio.setText(intent.getStringExtra("bio"))

        // Handle profile picture upload
        btnUpload.setOnClickListener {
            // Launch image picker or upload logic
        }

        // Save changes to Firebase
        btnSave.setOnClickListener {// NEED TO FIX
//            val updatedName = etName.text.toString()
//            val updatedBio = etBio.text.toString()
//
//            //val userRef = database.getReference("Users").child(currentUser?.uid ?: "")
//            val updates = mapOf(
//                "name" to updatedName,
//                "bio" to updatedBio
//                // Add profile picture URL if applicable
//            )
//
//            userRef.updateChildren(updates).addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show()
//                    finish() // Go back to ProfileFragment
//                } else {
//                    Toast.makeText(this, "Error updating profile", Toast.LENGTH_SHORT).show()
//                }
//            }
        }
    }
}
