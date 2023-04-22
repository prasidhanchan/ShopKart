package com.shoppy.shopkart.navigation

enum class NavScreens {
    SplashScreen,
    RegisterScreen,
    LoginScreen,
    MainScreenHolder,
    AdminScreen,
    AboutScreen,
    MyProfile,
    DetailsScreen;

    companion object{
        fun fromRoute(route: String): NavScreens
        = when(route.substringBefore(delimiter = "/")){
           SplashScreen.name -> SplashScreen
           RegisterScreen.name -> RegisterScreen
           LoginScreen.name -> LoginScreen
            MainScreenHolder.name -> MainScreenHolder
            AdminScreen.name -> AdminScreen
            AboutScreen.name -> AboutScreen
            MyProfile.name -> MyProfile
            DetailsScreen.name -> DetailsScreen
            else -> MainScreenHolder
        }
    }
}