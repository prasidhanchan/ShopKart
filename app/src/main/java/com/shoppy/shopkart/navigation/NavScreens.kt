package com.shoppy.shopkart.navigation

enum class NavScreens {
    SplashScreen,
    RegisterScreen,
    LoginScreen,
    HomeScreen,
    CartScreen,
    ProfileScreen,
    MoreScreen;

    companion object{
        fun fromRoute(route: String): NavScreens
        = when(route.substringBefore(delimiter = "/")){
           SplashScreen.name -> SplashScreen
           RegisterScreen.name -> RegisterScreen
           LoginScreen.name -> LoginScreen
           HomeScreen.name -> HomeScreen
           CartScreen.name -> CartScreen
           ProfileScreen.name -> ProfileScreen
           MoreScreen.name -> MoreScreen
            else -> HomeScreen
        }
    }
}