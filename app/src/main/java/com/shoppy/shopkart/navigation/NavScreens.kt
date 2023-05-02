package com.shoppy.shopkart.navigation

enum class NavScreens {
    SplashScreen,
    RegisterScreen,
    LoginScreen,
    MainScreenHolder,
    AdminScreen,
    AboutScreen,
    MyProfile,
    AddressScreen,
    EditAddressScreen,
    OrderSummaryScreen,
    PaymentScreen,
    OrderSuccessScreen;

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
            AddressScreen.name -> AddressScreen
            EditAddressScreen.name -> EditAddressScreen
            OrderSummaryScreen.name -> OrderSummaryScreen
            PaymentScreen.name -> PaymentScreen
            OrderSuccessScreen.name -> OrderSuccessScreen
            else -> MainScreenHolder
        }
    }
}