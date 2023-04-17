package com.shoppy.shopkart.screens.mainscreenholder

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.shoppy.shopkart.navigation.NavScreens
import kotlinx.coroutines.launch

class MainScreenViewModel: ViewModel() {

    private val mAuth = FirebaseAuth.getInstance()

    private val currentUser = mAuth.currentUser!!.uid

    fun checkAdmin(email: (String) -> Unit){

               viewModelScope.launch {

                   FirebaseFirestore.getInstance().collection("Users").document(currentUser).get()
                .addOnSuccessListener { document ->
//                    Log.d("EMAILS", "MainScreenHolder: ${document.data?.getValue("email")}")
                    email(document.data!!.getValue("email").toString())
                }
        }
    }

    fun signOut(navController: NavController){
        viewModelScope.launch {

            FirebaseAuth.getInstance().signOut().run {
                navController.popBackStack()
                navController.navigate(NavScreens.LoginScreen.name)}
        }
    }
}