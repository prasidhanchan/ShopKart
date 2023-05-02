package com.shoppy.shopkart.screens.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.LoadingComp
import com.shoppy.shopkart.components.OrdersCard
import com.shoppy.shopkart.models.MCart
import com.shoppy.shopkart.ui.theme.roboto

@Composable
fun OrdersScreen(navController: NavController,viewModel: MyOrderViewModel = hiltViewModel()){

    var orderList = emptyList<MCart>()
    val userId = FirebaseAuth.getInstance().currentUser?.uid
//    Log.d("ORDERSS", "OrdersScreen: $orderList")

    orderList = viewModel.fireOrder.value.data!!.toList().filter { mOrder ->

        userId == mOrder.user_id

    }

    Scaffold(topBar = { OrdersAppBar() }, modifier = Modifier.fillMaxSize(), backgroundColor = ShopKartUtils.offWhite) { innerPadding ->

        Column(modifier = Modifier
            .padding(innerPadding)
            .background(ShopKartUtils.offWhite)
            .fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {

            if (!viewModel.fireOrder.value.loading!!) {

                OrdersCard(cardList = orderList, navController = navController)
            }else{
                LoadingComp()
            }
        }

    }
}

@Composable
fun OrdersAppBar(){
    Column(verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)) {
        Text(text="My Orders",
            style= TextStyle(fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, fontFamily = roboto))
        Divider(modifier = Modifier.height(2.dp))
    }
}