package com.shoppy.shopkart.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shoppy.shopkart.screens.login.LoginScreen
import com.shoppy.shopkart.screens.register.RegisterScreen
import com.shoppy.shopkart.screens.SplashScreen
import com.shoppy.shopkart.screens.cart.CartScreen
import com.shoppy.shopkart.screens.home.HomeScreen
import com.shoppy.shopkart.screens.mainscreenholder.MainScreenHolder
import com.shoppy.shopkart.screens.profile.ProfileScreen
import com.shoppy.shopkart.screens.search.SearchScreen

@Composable
fun ShopKartNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavScreens.SplashScreen.name,){
        composable(NavScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }

        composable(NavScreens.LoginScreen.name){
            LoginScreen(navController = navController)
        }

        composable(NavScreens.RegisterScreen.name){
            RegisterScreen(navController = navController)
        }

        composable(NavScreens.MainScreenHolder.name){
            MainScreenHolder(navController = navController)
        }
    }

}