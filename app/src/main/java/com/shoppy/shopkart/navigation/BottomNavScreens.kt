package com.shoppy.shopkart.navigation

import com.shoppy.shopkart.R

sealed class BottomNavScreens(
    val route: String,
    val title: String,
    val icon: Int? = null
){

    object Home: BottomNavScreens(route = "home", title = "Home", icon = R.drawable.ic_home)
    object Orders: BottomNavScreens(route = "orders", title = "Orders", icon = R.drawable.ic_orders)
    object Cart: BottomNavScreens(route = "cart", title = "Cart", icon = R.drawable.ic_cart)
    object Profile: BottomNavScreens(route = "profile", title = "Profile", icon = R.drawable.ic_profile)
    object Details: BottomNavScreens(route = "details", title = "Details")
    object MyOrderDetails: BottomNavScreens(route = "myOrderDetails", title = "MyOrderDetails")
    object SearchScreen: BottomNavScreens(route = "searchScreen", title = "SearchScreen")
    object AdminScreen: BottomNavScreens(route = "adminScreen", title = "AdminScreen")
    object EmployeeScreen: BottomNavScreens(route = "employeeScreen", title = "EmployeeScreen")
    object AddRemoveBrandAdmin: BottomNavScreens(route = "addRemoveBrandAdmin", title = "AddRemoveBrandAdmin")
    object AddProductSliderAdmin: BottomNavScreens(route = "addProductSliderAdmin", title = "AddProductSliderAdmin")
    object AddRemoveBrandEmpl: BottomNavScreens(route = "addRemoveBrandEmpl", title = "AddRemoveBrandEmpl")
    object AddProductSliderEmpl: BottomNavScreens(route = "addProductSliderEmpl", title = "AddProductSliderEmpl")
    object AddEmployee: BottomNavScreens(route = "addEmployee", title = "AddEmployee")
    object OrderedItems: BottomNavScreens(route = "orderedItems", title = "OrderedItems")

    object Items{
        val list = listOf(
            Home,
            Orders,
            Cart,
            Profile,
        )
    }
}
