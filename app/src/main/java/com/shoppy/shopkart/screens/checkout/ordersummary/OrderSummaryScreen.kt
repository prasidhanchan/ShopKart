package com.shoppy.shopkart.screens.checkout.ordersummary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.BackButton
import com.shoppy.shopkart.components.OrderSummaryCard
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.components.ProgressBox
import com.shoppy.shopkart.models.MCart
import com.shoppy.shopkart.navigation.NavScreens
import com.shoppy.shopkart.ui.theme.roboto
import java.text.DecimalFormat

@Composable
fun OrderSummaryScreen(navController: NavHostController,viewModel: OrderSummaryScreenViewModel = hiltViewModel()) {

    var cartList = emptyList<MCart>()

    val userId = FirebaseAuth.getInstance().currentUser?.uid

    if (!viewModel.fireSummary.value.data.isNullOrEmpty()){

        cartList = viewModel.fireSummary.value.data!!.toList().filter { mCart ->

        mCart.user_id == userId
        }
    }
    
    val totalAmount = remember { mutableStateOf(0) }

//    viewModel.getEmailPhone(email = {})


    Scaffold(topBar = { BackButton(navController = navController, topBarTitle = "Order Summary", spacing = 60.dp) },
        backgroundColor = ShopKartUtils.offWhite, bottomBar = { SummaryBottomBar(totalAmount = totalAmount.value,navController = navController) }) { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //Progress Indicator 1-2-3
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp), elevation = 2.dp,
                color = Color.White
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    ProgressBox(number = "1", title = "Address", color = Color.Blue)
                    Divider(
                        modifier = Modifier
                            .height(2.dp)
                            .width(50.dp)
                    )
                    ProgressBox(number = "2", title = "Order Summary", color = Color.Blue)
                    Divider(
                        modifier = Modifier
                            .height(2.dp)
                            .width(50.dp)
                    )
                    ProgressBox(number = "3", title = "Payment", color = Color.Gray)
                }
            }

            //Items Count,Items Price... Card
            Card(modifier = Modifier
                .padding(10.dp)
                .height(125.dp)
                .width(350.dp), elevation = 2.dp,
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start, modifier = Modifier
                    .background(Color.White)
                    .padding(start = 35.dp, end = 15.dp)) {

                    RowComp(title = "Items Count:", price = "${cartList.size}", space = 120.dp)
                    RowComp(title = "Items Price:", price = "₹${ DecimalFormat("#,##,###").format(totalAmount.value.toDouble()) }", space = 120.dp)
                    RowComp(title = "Delivery Fee:", price = "₹${ DecimalFormat("#,##,###").format((100).toDouble()) }", space = 110.dp)
                    RowComp(title = "Total Price:", price = "₹${ DecimalFormat("#,##,###").format((totalAmount.value + 100).toDouble()) }", space = 125.dp)
                }
            }
            
            OrderSummaryCard(cardList = cartList, viewModel = viewModel){ price ->
                
                totalAmount.value = price

            }
        }
    }
}

@Composable
fun SummaryBottomBar(totalAmount:Int,navController: NavController){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)) {

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally) {

//            Text(text = "Total Amount: ₹${DecimalFormat("#,##,###").format(totalAmount.toString().toDouble())}", style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold))

            PillButton(title = "Continue", color = Color(0XFF000000).toArgb()){ navController.navigate(NavScreens.PaymentScreen.name + "/${totalAmount}") }
        }

    }
}

@Composable
fun RowComp(title: String,price:String,space: Dp) {
    Row(modifier = Modifier.padding(5.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {

        Text(text = title, style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = roboto))

        Spacer(modifier = Modifier.width(space))

        Text(text = price, style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold), fontFamily = roboto)
    }
}