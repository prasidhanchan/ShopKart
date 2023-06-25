package com.shoppy.shopkart.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shoppy.shopkart.screens.ForgotPasswordScreen
import com.shoppy.shopkart.screens.SplashScreen
import com.shoppy.shopkart.screens.checkout.OrderSuccessScreen
import com.shoppy.shopkart.screens.checkout.address.AddressScreen
import com.shoppy.shopkart.screens.checkout.address.EditAddressScreen
import com.shoppy.shopkart.screens.checkout.ordersummary.OrderSummaryScreen
import com.shoppy.shopkart.screens.checkout.payment.PaymentScreen
import com.shoppy.shopkart.screens.login.LoginScreen
import com.shoppy.shopkart.screens.login.LoginScreen2
import com.shoppy.shopkart.screens.mainscreenholder.MainScreenHolder
import com.shoppy.shopkart.screens.myprofile.MyProfileScreen
import com.shoppy.shopkart.screens.register.RegisterScreen

@Composable
fun ShopKartNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavScreens.SplashScreen.name){
        composable(NavScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }

        composable(NavScreens.LoginScreen.name){
            LoginScreen2(navController = navController)
        }

        composable(NavScreens.RegisterScreen.name){
            RegisterScreen(navController = navController)
        }

        composable(NavScreens.MainScreenHolder.name){
            MainScreenHolder(navController = navController)
        }

        composable(NavScreens.ForgotPasswordScreen.name) {
            ForgotPasswordScreen(navHostController = navController)
        }

    }

}