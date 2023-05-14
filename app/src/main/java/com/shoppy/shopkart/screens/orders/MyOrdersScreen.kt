package com.shoppy.shopkart.screens.orders

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.LoadingComp
import com.shoppy.shopkart.components.OrdersCard
import com.shoppy.shopkart.models.MCart
import com.shoppy.shopkart.models.MOrder
import com.shoppy.shopkart.ui.theme.roboto

@Composable
fun OrdersScreen(navController: NavController,viewModel: MyOrderViewModel = hiltViewModel()){

    var orderList = emptyList<MOrder>()
    val userId = FirebaseAuth.getInstance().currentUser?.uid
//    Log.d("ORDERSS", "OrdersScreen: $orderList")

    orderList = viewModel.fireOrder.value.data!!.toList().filter { mOrder ->

        userId == mOrder.user_id

    }

    Scaffold(topBar = { OrdersAppBar() }, modifier = Modifier.fillMaxSize(), backgroundColor = ShopKartUtils.offWhite) { innerPadding ->

        Column(modifier = Modifier
            .padding(innerPadding)
//            .padding(bottom = 100.dp)
//            .verticalScroll(rememberScrollState())
            .background(ShopKartUtils.offWhite)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween) {

            if (!viewModel.fireOrder.value.loading!!) {

                OrdersCard(cardList = orderList, navController = navController)

                Spacer(modifier = Modifier.height(120.dp))

            }else{
                LoadingComp()
            }

            //If no Orders Show Empty Orders Screen
            if (orderList.isEmpty()) Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {

                Image(painter = painterResource(id = R.drawable.empty_cart), contentDescription = "No Orders", contentScale = ContentScale.Crop, modifier = Modifier
                    .size(300.dp)
                    .padding(bottom = 10.dp)
                    .clip(CircleShape))
                Text(text = "No Orders\n  Order Something!", textAlign = TextAlign.Center, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = roboto))

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
        Divider(modifier = Modifier
            .height(2.dp)
            .width(320.dp))
    }
}