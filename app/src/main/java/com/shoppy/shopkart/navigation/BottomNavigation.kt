package com.shoppy.shopkart.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shoppy.shopkart.screens.cart.CartScreen
import com.shoppy.shopkart.screens.home.HomeScreen
import com.shoppy.shopkart.screens.home.HomeViewModel
import com.shoppy.shopkart.screens.orders.OrdersScreen
import com.shoppy.shopkart.screens.profile.ProfileScreen

@Composable
fun BottomNavigation(navController: NavHostController,
                     email: String,
                     admin: () -> Unit,
                     about: () -> Unit,
                     myProfile: () -> Unit,
                     signOut: () -> Unit){
    NavHost(navController = navController, startDestination = BottomNavScreens.Home.route){
        composable(BottomNavScreens.Home.route){
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(navController = navController,viewModel)
        }

        composable(BottomNavScreens.Orders.route){
            OrdersScreen(navController = navController)
        }

        composable(BottomNavScreens.Cart.route){
            CartScreen(navController = navController)
        }

        composable(BottomNavScreens.Profile.route){
            ProfileScreen(navController = navController,
                email = email,
                admin = admin,
                about = about,
                myProfile = myProfile){
                signOut()
            }
        }
    }
}