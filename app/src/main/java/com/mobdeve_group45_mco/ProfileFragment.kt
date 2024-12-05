package com.mobdeve_group45_mco

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mobdeve_group45_mco.databinding.FragmentProfileBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewBinding: FragmentProfileBinding
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var db: FirebaseFirestore
    private lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        db = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = auth.currentUser

        // Update UI elements based on user authentication state
        if (currentUser == null) {
            // User is not logged in, hide edit profile button
            viewBinding.fragmentProfileBtnEditProfile.visibility = View.GONE

            // Update logout button to "Sign In" with green color
            val logoutBtn = view.findViewById<Button>(R.id.fragment_profile_btn_Logout)
            logoutBtn.text = "Sign In"
            logoutBtn.setBackgroundColor(Color.parseColor("#32C777"))

            logoutBtn.setOnClickListener {
                // Navigate to login page
                val navIntent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(navIntent)
            }

        } else {
            // User is logged in, show edit profile button
            viewBinding.fragmentProfileBtnEditProfile.visibility = View.VISIBLE

            // Set up logout button
            val logoutBtn = view.findViewById<Button>(R.id.fragment_profile_btn_Logout)
//            logoutBtn.text = "Logout"
//            logoutBtn.setBackgroundColor(Color.parseColor("#FF0000")) // Default logout color

            logoutBtn.setOnClickListener {
                AuthUI.getInstance()
                    .signOut(requireContext())
                    .addOnCompleteListener {
                        // Navigate to the login page and finish the current MainActivity
                        val navIntent = Intent(requireContext(), LoginActivity::class.java)
                        navIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(navIntent)
                    }
            }

            viewBinding.fragmentProfileBtnEditProfile.setOnClickListener {
                val intent = Intent(requireContext(), EditProfile::class.java)
                intent.putExtra("bio", viewBinding.fragmentProfileTvBio.text.toString())
                startActivity(intent)
            }
        }

        loadUserProfile()
    }

    private fun loadImageFromFirebaseStorage(imagePath: String) {
        val storageRef = storage.reference.child(imagePath)

        // Get the download URL and load it using Glide
        storageRef.downloadUrl.addOnSuccessListener { uri ->
            // Using Glide to load the image into ImageView

            Glide.with(this)
                .load(uri) // URI of the image
                .into(viewBinding.fragmentProfileImgProfile) // ImageView to set the image in
        }.addOnFailureListener { e ->
//            Toast.makeText(this, "Failed to load image: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadUserProfile() {
        val currentUser = auth.currentUser ?: return

        db.collection("users")
            .document(currentUser.uid)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    // Get user data from Firestore
                    val name = document.getString("name") ?: currentUser.displayName
                    val bio = document.getString("bio") ?: ""
                    val profilePicUrl = document.getString("profile_pic")

                    // Update UI with user data
                    viewBinding.fragmentProfileTvName.text = name
                    viewBinding.fragmentProfileTvBio.text = bio
                    profilePicUrl?.let {
                            Log.i("hi", profilePicUrl)
                            loadImageFromFirebaseStorage(profilePicUrl)
                        }
                } else {
                    // If document doesn't exist, create it with default values
                    val userProfile = UserProfile(
                        name = currentUser.displayName ?: "",
                        bio = ""
                    )

                    db.collection("users")
                        .document(currentUser.uid)
                        .set(userProfile)
                        .addOnSuccessListener {
                            // After creating the document, display the default values
                            viewBinding.fragmentProfileTvName.text = currentUser.displayName
                            viewBinding.fragmentProfileTvBio.text = ""
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(requireContext(), "Error creating profile: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "PFpage error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onResume() {
        super.onResume()
        loadUserProfile()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}