package com.shoppy.shopkart.screens.mainscreenholder

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shoppy.shopkart.navigation.BottomNavBar
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.navigation.BottomNavigation
import com.shoppy.shopkart.navigation.NavScreens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreenHolder(navController: NavController,viewModel: MainScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){

    val currentScreen = remember {
        mutableStateOf<BottomNavScreens>(BottomNavScreens.Home)
    }

    val emailState = remember {
        mutableStateOf("admin.kawaki@gmail.com")
    }

    viewModel.checkAdmin { email ->
//        Log.d("BUGSS", "MainScreenHolder: $email"
        emailState.value = email
    }


    val navHostController = rememberNavController()

    Scaffold(bottomBar = { BottomNavBar(currentScreen = currentScreen.value.route,
        navHostController = navHostController){
        currentScreen.value = it
    } }) {
        BottomNavigation(navController = navHostController,
            email = emailState.value,
            admin = { navController.navigate(NavScreens.AdminScreen.name) },
            about = { navController.navigate(NavScreens.AboutScreen.name) },
            myProfile = {navController.navigate(NavScreens.MyProfile.name)}){
            viewModel.signOut(navController = navController)}
    }
}
