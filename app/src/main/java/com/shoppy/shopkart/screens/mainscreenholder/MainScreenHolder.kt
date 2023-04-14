package com.shoppy.shopkart.screens.mainscreenholder

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.shoppy.shopkart.navigation.BottomNavBar
import com.shoppy.shopkart.navigation.BottomNavigation
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.navigation.NavScreens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreenHolder(navController: NavController){

    val currentScreen = remember {
        mutableStateOf<BottomNavScreens>(BottomNavScreens.Home)
    }

    val navHostController = rememberNavController()

    Scaffold(bottomBar = { BottomNavBar(currentScreen = currentScreen.value.route,navHostController = navHostController){
        currentScreen.value = it
    } }) {
        BottomNavigation(navController = navHostController){
            FirebaseAuth.getInstance().signOut().run {
                navController.popBackStack()
                navController.navigate(NavScreens.LoginScreen.name) }
        }
    }
}
