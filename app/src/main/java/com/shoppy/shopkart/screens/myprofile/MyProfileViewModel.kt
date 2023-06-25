package com.shoppy.shopkart.screens.myprofile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch

class MyProfileViewModel: ViewModel() {

    val currentUser = FirebaseAuth.getInstance().currentUser
    private val db = FirebaseFirestore.getInstance().collection("Users").document(currentUser?.email!!)
    private val storageRef = FirebaseStorage.getInstance().reference.child("ProfileImages").child(System.currentTimeMillis().toString())
//Retrieving Profile details from firebase
    fun getMyProfile(profileImage:(String?) -> Unit,name:(String) -> Unit,email:(String) -> Unit,phone:(String) -> Unit,address:(String) -> Unit){

        viewModelScope.launch {

            db.get().addOnSuccessListener { doc ->

                profileImage(doc.data?.getValue("profile_image").toString())
                name(doc.data?.getValue("name").toString())
                email(doc.data?.getValue("email").toString())
                phone(doc.data?.getValue("phone_no").toString())
                address(doc.data?.getValue("address").toString())

            }
        }
    }

    fun updateProfileImage(imageUrl: Uri?,name: String,email: String = "",phone: String,address: String){

        viewModelScope.launch {
            if (imageUrl != null) {

                storageRef.putFile(imageUrl).addOnSuccessListener {

                    storageRef.downloadUrl.addOnSuccessListener { url ->
                        db.update("profile_image", url)
                    }
                }
            }

            //Updating user values
            db.update("name",name)
//            db.update("email",email)
            db.update("phone_no",phone)
            db.update("address",address)
        }
    }

    fun removeProfilePhoto(successRemovePic: () -> Unit){
        db.update("profile_image","").addOnSuccessListener { successRemovePic.invoke() }
    }
}