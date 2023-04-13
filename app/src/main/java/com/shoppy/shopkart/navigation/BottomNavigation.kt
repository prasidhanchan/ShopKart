package com.shoppy.shopkart.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shoppy.shopkart.screens.cart.CartScreen
import com.shoppy.shopkart.screens.home.HomeScreen
import com.shoppy.shopkart.screens.login.LoginScreen
import com.shoppy.shopkart.screens.profile.ProfileScreen
import com.shoppy.shopkart.screens.search.SearchScreen
import kotlin.math.sign

@Composable
fun BottomNavigation(navController: NavHostController,signOut: () -> Unit){
    NavHost(navController = navController, startDestination = BottomNavScreens.Home.route){
        composable(BottomNavScreens.Home.route){
            HomeScreen(navController = navController)
        }

        composable(BottomNavScreens.Search.route){
            SearchScreen(navController = navController)
        }

        composable(BottomNavScreens.Cart.route){
            CartScreen(navController = navController)
        }

        composable(BottomNavScreens.Profile.route){
            ProfileScreen(navController = navController){
                signOut()
            }
        }
    }
}