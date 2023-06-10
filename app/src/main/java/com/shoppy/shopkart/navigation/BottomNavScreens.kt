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
    object MyProfile: BottomNavScreens(route = "myProfile", title = "MyProfile")
    object About: BottomNavScreens(route = "about", title = "About")
    object Details: BottomNavScreens(route = "details", title = "Details")
    object MyOrderDetails: BottomNavScreens(route = "myOrderDetails", title = "MyOrderDetails")
    object SearchScreen: BottomNavScreens(route = "searchScreen", title = "SearchScreen")
    object AdminScreen: BottomNavScreens(route = "adminScreen", title = "AdminScreen")
    object EmployeeScreen: BottomNavScreens(route = "employeeScreen", title = "EmployeeScreen")
    object AddRemoveBrandAdmin: BottomNavScreens(route = "addRemoveBrandAdmin", title = "AddRemoveBrandAdmin")
    object AddProductSliderAdmin: BottomNavScreens(route = "addProductSliderAdmin", title = "AddProductSliderAdmin")
    object EmployeeAttendance: BottomNavScreens(route = "employeeAttendance", title = "EmployeeAttendance")
    object AddRemoveBrandEmpl: BottomNavScreens(route = "addRemoveBrandEmpl", title = "AddRemoveBrandEmpl")
    object AddProductSliderEmpl: BottomNavScreens(route = "addProductSliderEmpl", title = "AddProductSliderEmpl")
    object AddEmployee: BottomNavScreens(route = "addEmployee", title = "AddEmployee")
    object OrderedItems: BottomNavScreens(route = "orderedItems", title = "OrderedItems")
    object OnTheWayItems: BottomNavScreens(route = "onTheWayItems", title = "OnTheWayItems")
    object DeliveredItems: BottomNavScreens(route = "deliveredItems", title = "DeliveredItems")
    object OrderedItemsEmp: BottomNavScreens(route = "orderedItemsEmp", title = "OrderedItemsEmp")
    object OnTheWayItemsEmp: BottomNavScreens(route = "onTheWayItemsEmp", title = "OnTheWayItemsEmp")
    object DeliveredItemsEmp: BottomNavScreens(route = "deliveredItemsEmp", title = "DeliveredItemsEmp")
    object AddressScreen: BottomNavScreens(route = "addressScreen", title = "AddressScreen")
    object EditAddressScreen: BottomNavScreens(route = "editAddressScreen", title = "EditAddressScreen")
    object OrderSummaryScreen: BottomNavScreens(route = "orderSummaryScreen", title = "OrderSummaryScreen")
    object PaymentScreen: BottomNavScreens(route = "paymentScreen", title = "PaymentScreen")
    object OrderSuccessScreen: BottomNavScreens(route = "orderSuccessScreen", title = "OrderSuccessScreen")
//    object ForgotPasswordScreen: BottomNavScreens(route = "forgotPasswordScreen", title = "ForgotPasswordScreen")

    object Items{
        val list = listOf(
            Home,
            Orders,
            Cart,
            Profile,
        )
    }

    object ItemsAdmin{
        val list = listOf(
            Home,
            Profile,
        )
    }
}
