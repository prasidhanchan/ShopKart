package com.shoppy.shopkart.screens.mainscreenholder

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shoppy.shopkart.navigation.BottomNavBar
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.navigation.BottomNavigation
import com.shoppy.shopkart.navigation.NavScreens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreenHolder(navController: NavController,viewModel: MainScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){

    val navHostController = rememberNavController()

    var showBottomBar by rememberSaveable { mutableStateOf(true) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = remember { mutableStateOf<BottomNavScreens>(BottomNavScreens.Home) }
    val currentScreenEntry = navBackStackEntry?.destination

//    val emailState = remember { mutableStateOf("admin.kawaki@gmail.com") }
    val emailState = remember { mutableStateOf("") }

    viewModel.checkAdmin { email ->
//        Log.d("BUGSS", "MainScreenHolder: $email"
        emailState.value = email
    }


//    showBottomBar = when(navBackStackEntry?.destination?.route){
//        BottomNavScreens.Home.route -> true
//        BottomNavScreens.Orders.route -> true
//        BottomNavScreens.Profile.route -> true
//        BottomNavScreens.Cart.route -> true
//        BottomNavScreens.Details.route -> false
//        else -> true
//    }

    Scaffold(bottomBar = {
        BottomNavBar(
            navHostController = navHostController){
            currentScreen.value = it
        }}) {

//        Log.d("SHOW", "MainScreenHolder: ${currentScreen.value.route}")
        BottomNavigation(navController = navHostController,
            email = emailState.value,
            admin = { navController.navigate(NavScreens.AdminScreen.name) },
            about = { navController.navigate(NavScreens.AboutScreen.name) },
            naviAddress = { navController.navigate(NavScreens.AddressScreen.name) },
            myProfile = {navController.navigate(NavScreens.MyProfile.name)}){
            viewModel.signOut(navController = navController)}
    }
}
