package com.shoppy.shopkart.screens.mainscreenholder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.shoppy.shopkart.navigation.NavScreens
import kotlinx.coroutines.launch

class MainScreenViewModel: ViewModel() {

    private val mAuth = FirebaseAuth.getInstance()

    private val currentUser = mAuth.currentUser!!.uid

    fun checkAdminAndEmployee(email: (String?) -> Unit){

               viewModelScope.launch {
                    email(mAuth.currentUser?.email)
                }
        }

    fun signOut(navController: NavController, oneTapClient: SignInClient){
        viewModelScope.launch {
            oneTapClient.signOut()
            mAuth.signOut().run {
                navController.popBackStack()
                navController.navigate(NavScreens.LoginScreen.name)}
        }
    }
}