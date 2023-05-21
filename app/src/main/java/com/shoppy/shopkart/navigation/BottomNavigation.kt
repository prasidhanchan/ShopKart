package com.shoppy.shopkart.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.shoppy.shopkart.screens.admin.AddEmployee
import com.shoppy.shopkart.screens.admin.AddProductSliderAdmin
import com.shoppy.shopkart.screens.admin.AddRemoveBrandAdmin
import com.shoppy.shopkart.screens.admin.AdminScreen
import com.shoppy.shopkart.screens.admin.orderstatus.DeliveredItems
import com.shoppy.shopkart.screens.admin.orderstatus.OnTheWayItems
import com.shoppy.shopkart.screens.admin.orderstatus.OrderedItems
import com.shoppy.shopkart.screens.details.DetailsScreen
import com.shoppy.shopkart.screens.cart.CartScreen
import com.shoppy.shopkart.screens.cart.CartScreenViewModel
import com.shoppy.shopkart.screens.employee.AddProductSliderEmpl
import com.shoppy.shopkart.screens.employee.AddRemoveBrandEmpl
import com.shoppy.shopkart.screens.employee.EmployeeScreen
import com.shoppy.shopkart.screens.employee.orderstatus.DeliveredItemsEmp
import com.shoppy.shopkart.screens.employee.orderstatus.OnTheWayItemsEmp
import com.shoppy.shopkart.screens.employee.orderstatus.OrderedItemsEmp
import com.shoppy.shopkart.screens.home.HomeScreen
import com.shoppy.shopkart.screens.home.HomeViewModel
import com.shoppy.shopkart.screens.myorderdetails.MyOrderDetailsScreen
import com.shoppy.shopkart.screens.orders.OrdersScreen
import com.shoppy.shopkart.screens.profile.ProfileScreen
import com.shoppy.shopkart.screens.search.SearchScreen


//BottomNavScreens.Home.route
@Composable
fun BottomNavigation(navController: NavHostController,
                     email: String,
//                     admin: () -> Unit,
                     about: () -> Unit,
                     myProfile: () -> Unit,
                     naviAddress:() -> Unit,
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
            CartScreen(navController = navController,viewModel, naviAddress = {naviAddress.invoke()})
        }

        composable(BottomNavScreens.Profile.route) {
            ProfileScreen(
                navController = navController,
                email = email,
//                admin = admin,
                about = about,
                myProfile = myProfile
            ) {
                signOut()
            }
        }

        val detailsScreen = BottomNavScreens.Details.route
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

        val myOrderDetails = BottomNavScreens.MyOrderDetails.route
        composable("$myOrderDetails/{status}/{product_title}/{product_url}/{product_price}/{quantity}/{payment_method}/{order_id}/{order_date}", arguments = listOf(
            navArgument("status"){
                type = NavType.StringType
            },

            navArgument("product_title"){
                type = NavType.StringType
            },

            navArgument("product_url"){
                type = NavType.StringType
            },

            navArgument("product_price"){
                type = NavType.IntType
            },

            navArgument("quantity"){
                type = NavType.IntType
            },

            navArgument("payment_method"){
                type = NavType.StringType
            },

            navArgument("order_id"){
                type = NavType.StringType
            },

            navArgument("order_date"){
                type = NavType.StringType
            },
        )) { bacStack ->
            val status = bacStack.arguments?.getString("status")
            val productTitle = bacStack.arguments?.getString("product_title")
            val productUrl = bacStack.arguments?.getString("product_url")
            val productPrice = bacStack.arguments?.getInt("product_price")
            val quantity = bacStack.arguments?.getInt("quantity")
            val paymentMethod = bacStack.arguments?.getString("payment_method")
            val orderId = bacStack.arguments?.getString("order_id")
            val orderDate = bacStack.arguments?.getString("order_date")
            MyOrderDetailsScreen(navController = navController,
                status = status!!,
                product_title = productTitle!!,
                product_url = productUrl!!,
                product_price = productPrice!!,
                quantity = quantity!!,
                payment_method = paymentMethod!!,
                order_id = orderId!!,
                order_date = orderDate!!
            )
        }

        composable(BottomNavScreens.SearchScreen.route) {
            SearchScreen(navController = navController)
        }

        composable(BottomNavScreens.AdminScreen.route) {
            AdminScreen(navController = navController)
        }

        composable(BottomNavScreens.EmployeeScreen.route) {
            EmployeeScreen(navController = navController)
        }

        composable(BottomNavScreens.AddRemoveBrandAdmin.route) {
            AddRemoveBrandAdmin(navHostController = navController)
        }

        composable(BottomNavScreens.AddProductSliderAdmin.route) {
            AddProductSliderAdmin(navHostController = navController)
        }

        composable(BottomNavScreens.AddRemoveBrandEmpl.route) {
            AddRemoveBrandEmpl(navHostController = navController)
        }

        composable(BottomNavScreens.AddProductSliderEmpl.route) {
            AddProductSliderEmpl(navHostController = navController)
        }

        composable(BottomNavScreens.AddEmployee.route) {
            AddEmployee(navHostController = navController)
        }

        composable(BottomNavScreens.OrderedItems.route) {
            OrderedItems(navHostController = navController)
        }

        composable(BottomNavScreens.OnTheWayItems.route) {
            OnTheWayItems(navHostController = navController)
        }

        composable(BottomNavScreens.DeliveredItems.route) {
            DeliveredItems(navHostController = navController)
        }

        composable(BottomNavScreens.OrderedItemsEmp.route) {
            OrderedItemsEmp(navHostController = navController)
        }

        composable(BottomNavScreens.OnTheWayItemsEmp.route) {
            OnTheWayItemsEmp(navHostController = navController)
        }

        composable(BottomNavScreens.DeliveredItemsEmp.route) {
            DeliveredItemsEmp(navHostController = navController)
        }

    }
}