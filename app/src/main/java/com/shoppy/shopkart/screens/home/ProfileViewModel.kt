package com.shoppy.shopkart.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileViewModel: ViewModel() {

    fun getUserName(user: (String) -> Unit) {

        val mAuth = FirebaseAuth.getInstance()

        val currentUser = mAuth.currentUser!!.uid

        val mFirestore = FirebaseFirestore.getInstance().collection("Users").document(currentUser).get()
                .addOnSuccessListener { document ->
//                    Log.d("UNAME", "HomeScreen: ${document.data!!.getValue("name")}")
                    user(document.data!!.getValue("name").toString())
                }
    }
}