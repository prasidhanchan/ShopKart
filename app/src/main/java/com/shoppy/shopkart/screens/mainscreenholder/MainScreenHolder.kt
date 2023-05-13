package com.shoppy.shopkart.screens.mainscreenholder

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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

//    val showBottomBar by rememberSaveable { mutableStateOf(true) }
//    var showBottomBar = true

//    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()

    val currentScreen = remember { mutableStateOf<BottomNavScreens>(BottomNavScreens.Home) }
//    val currentScreenEntry = navBackStackEntry?.destination

//    val emailState = remember { mutableStateOf("admin.kawaki@gmail.com") }
    val emailState = remember { mutableStateOf("") }

    viewModel.checkAdmin { email ->
//        Log.d("BUGSS", "MainScreenHolder: $email"
        emailState.value = email
    }


    val showBottomBar = when(navBackStackEntry?.destination?.route){
        BottomNavScreens.Details.route + "/{imageUrl}/{productTitle}/{productDescription}/{productPrice}" -> false
        BottomNavScreens.MyOrderDetails.route + "/{status}/{product_title}/{product_url}/{product_price}/{quantity}/{payment_method}/{order_id}/{order_date}" -> false
        BottomNavScreens.SearchScreen.route -> false
        else -> true
    }

//    showBottomBar = currentScreen.value.route != BottomNavScreens.Details.route

    Scaffold(bottomBar = {
        AnimatedVisibility(visible = showBottomBar, enter = fadeIn(animationSpec = tween(200)), exit = fadeOut(animationSpec = tween(200))) {

            BottomNavBar(navHostController = navHostController){ currentScreen.value = it }
        }
    }) {

        Log.d("SHOW", "MainScreenHolder1: ${currentScreen.value.route}")
        Log.d("SHOW", "MainScreenHolder2: ${navBackStackEntry?.destination?.route}")
        Log.d("SHOW", "MainScreenHolder3: ${navHostController.findDestination(BottomNavScreens.Details.route)}")
        BottomNavigation(navController = navHostController,
            email = emailState.value,
            admin = { navController.navigate(NavScreens.AdminScreen.name) },
            about = { navController.navigate(NavScreens.AboutScreen.name) },
            naviAddress = { navController.navigate(NavScreens.AddressScreen.name) },
            myProfile = {navController.navigate(NavScreens.MyProfile.name)}){
            viewModel.signOut(navController = navController)}
    }
}
