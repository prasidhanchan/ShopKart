package com.shoppy.shopkart.navigation

enum class NavScreens {
    SplashScreen,
    RegisterScreen,
    LoginScreen,
    MainScreenHolder;

    companion object{
        fun fromRoute(route: String): NavScreens
        = when(route.substringBefore(delimiter = "/")){
           SplashScreen.name -> SplashScreen
           RegisterScreen.name -> RegisterScreen
           LoginScreen.name -> LoginScreen
            MainScreenHolder.name -> MainScreenHolder
            else -> MainScreenHolder
        }
    }
}