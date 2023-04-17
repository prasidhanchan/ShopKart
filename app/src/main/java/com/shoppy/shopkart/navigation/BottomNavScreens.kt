package com.shoppy.shopkart.navigation

import androidx.compose.foundation.Image
import androidx.compose.material.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.shoppy.shopkart.R

sealed class BottomNavScreens(
    var route: String,
    val title: String,
    val icon: Int
){

    object Home: BottomNavScreens(route = "home", title = "Home", icon = R.drawable.ic_home)
    object Orders: BottomNavScreens(route = "orders", title = "Orders", icon = R.drawable.ic_orders)
    object Cart: BottomNavScreens(route = "cart", title = "Cart", icon = R.drawable.ic_cart)
    object Profile: BottomNavScreens(route = "profile", title = "Profile", icon = R.drawable.ic_profile)

    object Items{
        val list = listOf(
            Home,
            Orders,
            Cart,
            Profile
        )
    }
}
