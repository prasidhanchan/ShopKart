package com.shoppy.shopkart.screens.mainscreenholder

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.shoppy.shopkart.navigation.BottomNavBar
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.navigation.BottomNavigation
import com.shoppy.shopkart.navigation.NavScreens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreenHolder(navController: NavController,viewModel: MainScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){

    val navHostController = rememberNavController()

//    val showBottomBar by rememberSaveable { mutableStateOf(true) }
//    var showBottomBar = true

//    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()

    val currentScreen = remember { mutableStateOf<BottomNavScreens>(BottomNavScreens.Home) }
//    val currentScreenEntry = navBackStackEntry?.destination

    val emailState = remember { mutableStateOf("") }

    val context = LocalContext.current

    viewModel.checkAdminAndEmployee { email ->
        if (email != null) emailState.value = email
    }


//    val showBottomBar = when(navBackStackEntry?.destination?.route){
//        BottomNavScreens.Details.route + "/{imageUrl}/{productTitle}/{productDescription}/{productPrice}" -> false
//        BottomNavScreens.MyOrderDetails.route + "/{status}/{product_title}/{product_url}/{product_price}/{quantity}/{payment_method}/{order_id}/{order_date}" -> false
//        BottomNavScreens.SearchScreen.route -> false
//        BottomNavScreens.AdminScreen.route -> false
//        BottomNavScreens.EmployeeScreen.route -> false
//        BottomNavScreens.AddRemoveBrandAdmin.route -> false
//        BottomNavScreens.AddProductSliderAdmin.route -> false
//        BottomNavScreens.AddRemoveBrandEmpl.route -> false
//        BottomNavScreens.AddProductSliderEmpl.route -> false
//        BottomNavScreens.AddEmployee.route -> false
//        BottomNavScreens.OrderedItems.route -> false
//        else -> true
//    }

    val showBottomBar = when(navBackStackEntry?.destination?.route){
        BottomNavScreens.Home.route -> true
        BottomNavScreens.Orders.route -> true
        BottomNavScreens.Cart.route -> true
        BottomNavScreens.Profile.route -> true
        else -> false
    }

    Scaffold(bottomBar = {
        AnimatedVisibility(visible = showBottomBar, enter = fadeIn(animationSpec = tween(200)), exit = fadeOut(animationSpec = tween(200))) {

            BottomNavBar(navHostController = navHostController){ currentScreen.value = it }
        }
    }) {

        BottomNavigation(navController = navHostController,
            email = emailState.value,
        ){
            viewModel.signOut(navController = navController, oneTapClient = Identity.getSignInClient(context))}
    }
}
