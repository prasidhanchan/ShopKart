package com.shoppy.shopkart.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.shoppy.shopkart.models.MUser
import kotlinx.coroutines.launch

class RegisterViewModel:ViewModel() {

    private val mAuth: FirebaseAuth = Firebase.auth


    fun createUser(email: String,password: String,
                   nav: () -> Unit = {},
                   regExcept: (String) -> Unit = {}){
        viewModelScope.launch {

            mAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener {
                    nav()
                }.addOnFailureListener {
                    regExcept(it.message.toString())
                }
        }
    }

    fun addUserToDB(uName: String,
                    uEmail: String,
                    uPassword: String,
                    uPhone: String,
                    uAddress: String){

        val userId = mAuth.currentUser?.uid

       val user = MUser(id = userId,
           name = uName,
           email = uEmail,
           password = uPassword,
           phone_no = uPhone,
           address = uAddress,
           profile_image = "").convertToMap()

        val fb = FirebaseFirestore.getInstance().collection("Users").document(uEmail)

        fb.set(user)
    }
}