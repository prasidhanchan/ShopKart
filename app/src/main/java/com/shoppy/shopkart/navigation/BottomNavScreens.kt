package com.shoppy.shopkart.navigation

import com.shoppy.shopkart.R

sealed class BottomNavScreens(
    val route: String,
    val title: String,
    val icon: Int? = null,
    val show: Boolean
){

    object Home: BottomNavScreens(route = "home", title = "Home", icon = R.drawable.ic_home, show = true)
    object Orders: BottomNavScreens(route = "orders", title = "Orders", icon = R.drawable.ic_orders, show = true)
    object Cart: BottomNavScreens(route = "cart", title = "Cart", icon = R.drawable.ic_cart, show = true)
    object Profile: BottomNavScreens(route = "profile", title = "Profile", icon = R.drawable.ic_profile, show = true)
    object Details: BottomNavScreens(route = "details", title = "Details", show = false)
    object MyOrderDetails: BottomNavScreens(route = "myOrderDetails", title = "MyOrderDetails", show = false)
    object SearchScreen: BottomNavScreens(route = "searchScreen", title = "SearchScreen", show = false)

    object Items{
        val list = listOf(
            Home,
            Orders,
            Cart,
            Profile,
        )
    }
}
