package com.shoppy.shopkart.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.shoppy.shopkart.screens.details.DetailsScreen
import com.shoppy.shopkart.screens.cart.CartScreen
import com.shoppy.shopkart.screens.cart.CartScreenViewModel
import com.shoppy.shopkart.screens.home.HomeScreen
import com.shoppy.shopkart.screens.home.HomeViewModel
import com.shoppy.shopkart.screens.orders.OrdersScreen
import com.shoppy.shopkart.screens.profile.ProfileScreen


//BottomNavScreens.Home.route
@Composable
fun BottomNavigation(navController: NavHostController,
                     email: String,
                     admin: () -> Unit,
                     about: () -> Unit,
                     myProfile: () -> Unit,
                     signOut: () -> Unit, ) {
    NavHost(navController = navController, startDestination = BottomNavScreens.Home.route) {
        composable(BottomNavScreens.Home.route) {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(navController = navController, viewModel)
        }

        composable(BottomNavScreens.Orders.route) {
            OrdersScreen(navController = navController)
        }

        composable(BottomNavScreens.Cart.route) {
            val viewModel = hiltViewModel<CartScreenViewModel>()
            CartScreen(navController = navController,viewModel)
        }

        composable(BottomNavScreens.Profile.route) {
            ProfileScreen(
                navController = navController,
                email = email,
                admin = admin,
                about = about,
                myProfile = myProfile
            ) {
                signOut()
            }
        }

//        composable(NavScreens.DetailsScreen.name){
//            DetailsScreen(navController = navController, productData = )
//        }

        val detailsScreen = NavScreens.DetailsScreen.name
        composable("$detailsScreen/{imageUrl}/{productTitle}/{productDescription}/{productPrice}", arguments = listOf(
            navArgument("imageUrl") {
                type = NavType.StringType
            },
            navArgument("productTitle") {
                type = NavType.StringType
            },
            navArgument("productDescription") {
                type = NavType.StringType
            },

            navArgument("productPrice") {
                type = NavType.IntType
            }
        )) { backstack ->
            val imageUrl = backstack.arguments?.getString("imageUrl")
            val productTitle = backstack.arguments?.getString("productTitle")
            val productDescription = backstack.arguments?.getString("productDescription")
            val productPrice = backstack.arguments?.getInt("productPrice")
//            Log.d("TESTING", "BottomNavigation: $productDescription")
            DetailsScreen(
                navController = navController,
                imageUrl = imageUrl.toString(),
                productTitle = productTitle.toString(),
                productDescription = productDescription.toString(),
                productPrice = productPrice!!)
        }

    }
}