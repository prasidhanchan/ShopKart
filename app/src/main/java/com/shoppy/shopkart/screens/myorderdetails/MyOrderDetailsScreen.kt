package com.shoppy.shopkart.screens.myorderdetails

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.BackButton
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.components.ProgressBox
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.ui.theme.roboto
import java.text.DecimalFormat

@Composable
fun MyOrderDetailsScreen(navController: NavController,
                         status:String,
                         product_title: String,
                         product_url: String,
                         product_price: Int,
                         quantity: Int,
                         payment_method: String,
                         order_id: String,
                         order_date: String,
                         viewModel: MyOrderDetailsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val name = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }

    viewModel.getAddressNamePhone(
        name = { name.value = it },
        phone = { phone.value = it },
        address = { address.value = it })

    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            BackButton(
                navController = navController,
                spacing = 60.dp,
                topBarTitle = "Order Details"
            )
        }, modifier = Modifier
            .fillMaxSize(),
        backgroundColor = ShopKartUtils.offWhite
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            //Changing status color according to delivery_status
            val ordered = ShopKartUtils.blue
            var onTheWay = Color.Gray
            var delivered = Color.Gray

            if (status == "On The Way") {
                onTheWay = ShopKartUtils.blue
            } else if (status == "Delivered") {
                onTheWay = ShopKartUtils.blue
                delivered = ShopKartUtils.blue
            }

            //Progress Indicator 1-2-3
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
                    .height(80.dp),
                elevation = 2.dp,
                color = Color.White
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    ProgressBox(number = "1", title = "Ordered", color = ordered)
                    Divider(
                        modifier = Modifier
                            .height(2.dp)
                            .width(50.dp)
                    )
                    ProgressBox(number = "2", title = "On The Way", color = onTheWay)
                    Divider(
                        modifier = Modifier
                            .height(2.dp)
                            .width(50.dp)
                    )
                    ProgressBox(number = "3", title = "Delivered", color = delivered)
                }
            }

            //Product Image Card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .padding(start = 20.dp, end = 20.dp),
                shape = RoundedCornerShape(12.dp)
            ) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {

                    AsyncImage(
                        model = product_url,
                        contentDescription = product_title,
                        modifier = Modifier
                            .size(150.dp)
                            .padding(top = 8.dp),
                        contentScale = ContentScale.Fit,
                        placeholder = painterResource(id = R.drawable.placeholder)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
//                        .height(60.dp)
                            .padding(top = 8.dp, bottom = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = status,
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = roboto
                            )
                        )

                        //Changing logo as per delivery status
                        val logo = when (status) {
                            "On The Way" -> R.drawable.on_the_way
                            "Delivered" -> R.drawable.delivered
                            "Cancelled" -> R.drawable.cancel
                            else -> R.drawable.ordered
                        }

                        val tint =  if (status == "Cancelled") Color.Red else if (status == "Delivered") Color(0xFFCDDC39) else if (status == "On The Way") ShopKartUtils.blue else Color.Black

                        Icon(
                            modifier = Modifier
                                .padding(start = 5.dp)
                                .size(25.dp),
                            painter = painterResource(id = logo),
                            contentDescription = "Delivery Status",
                            tint = tint
                        )
                    }
                    Text(
                        text = product_title,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = roboto
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {


                        Text(
                            text = "₹${DecimalFormat("#,##,###").format(((product_price - 280) / quantity).toDouble())}",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = roboto
                            )
                        )

                        Text(
                            text = "Quantity: $quantity",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = roboto
                            )
                        )
                    }


                }

            }

            //Order Details Card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp),
                shape = RoundedCornerShape(12.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "Order Details",
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = roboto
                        ),
                        color = Color.Black.copy(alpha = 0.5f)
                    )
                    Text(
                        text = "Payment Method: $payment_method",
                        modifier = Modifier.padding(bottom = 10.dp),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = roboto
                        )
                    )
                    Text(
                        text = "Order Date: $order_date",
                        modifier = Modifier.padding(bottom = 10.dp),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = roboto
                        )
                    )
                    Text(
                        text = "Order ID: $order_id",
                        modifier = Modifier.padding(bottom = 10.dp),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = roboto
                        )
                    )
                }

            }

            //Shipping Address Card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(15.dp),
                shape = RoundedCornerShape(12.dp)
            ) {

                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {

                    Text(
                        text = "Shipping Address",
                        modifier = Modifier.padding(bottom = 8.dp),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = roboto
                        ),
                        color = Color.Black.copy(alpha = 0.5f)
                    )
                    Text(
                        text = name.value,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = roboto
                        )
                    )
                    Text(
                        text = address.value,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = roboto
                        )
                    )
                    Text(
                        text = "Phone no: ${phone.value}",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = roboto
                        )
                    )
                }

            }

            //Price Details Card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .padding(start = 20.dp, end = 20.dp),
                shape = RoundedCornerShape(12.dp)
            ) {

                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.Start
                ) {

                    Text(
                        text = "Price Details",
                        modifier = Modifier.padding(bottom = 8.dp),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = roboto
                        ),
                        color = Color.Black.copy(alpha = 0.5f)
                    )
                    Text(
                        //280 is price with delivery charge and GST 100 + 180
                        text = "Item Price: ₹${DecimalFormat("#,##,###").format(((product_price - 280) / quantity).toDouble())}",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = roboto
                        )
                    )
                    Text(
                        text = "Delivery Fee: ₹100",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = roboto
                        )
                    )

                    Text(
                        text = "GST: 18%",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = roboto
                        )
                    )
                    Text(
                        text = "Payment Method: $payment_method",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = roboto
                        )
                    )
                    Text(
                        text = "Total Price: ₹${DecimalFormat("#,##,###").format(product_price.toDouble())} (Incl. all taxes)",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = roboto
                        )
                    )
                }

            }

            //Hiding Cancel Order Button if product is already Delivered Or Cancelled
            val isEnabled = remember { mutableStateOf(true) }

            if (status == "Delivered" || status == "Cancelled" || status == "On The Way") isEnabled.value = false

            AnimatedVisibility(visible = isEnabled.value) {

                PillButton(
                    title = "Cancel Order", modifier = Modifier.padding(top = 30.dp, bottom = 20.dp), color = ShopKartUtils.black.toInt(), textColor = Color.Red) { openDialog.value = true }
            }

            //Calling Alert Dialog
            ShopKartDialog(openDialog = openDialog,
                onTap = { viewModel.cancelOrder(product_title = product_title)

                    //Navigating back to Orders Screen to refresh and pop backstack
                    navController.popBackStack()
                    navController.navigate(BottomNavScreens.Orders.route)
                        },
                context = context,
                navController = navController,
                title = "Cancel Order",
                subTitle = "Are You Sure, you want to cancel the order?",
                button1 = "Confirm",
                button2 = "Cancel",
                toast = "Order Cancelled")

//            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}

        //Cancel Order Alert Dialog
        @Composable
        fun ShopKartDialog(openDialog: MutableState<Boolean>, onTap: () -> Unit,context:Context,navController: NavController,title: String,subTitle: String,button1: String,button2: String,toast: String) {
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = { openDialog.value = false },
                    title = { Text(text = title, style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = roboto)) },
                    text = { Text(text = subTitle, style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = roboto)) },
                    confirmButton = {
                        TextButton(onClick = { onTap.invoke()
                            openDialog.value = false
                            Toast.makeText(context,toast,Toast.LENGTH_SHORT).show() },
                            colors = ButtonDefaults.buttonColors(Color.Black),
                            shape = RoundedCornerShape(12.dp)){ Text(text = button1, color = Color.White,style = TextStyle(fontSize = 12.sp,fontWeight = FontWeight.Bold, fontFamily = roboto)) }
                                    },
                    dismissButton = {
                        TextButton(onClick = { openDialog.value = false },
                            colors = ButtonDefaults.buttonColors(Color.Black),
                            shape = RoundedCornerShape(12.dp)){ Text(text = button2, color = Color.White, style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = roboto)) }
                                    },
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }

//    }TextButton(onClick = {  }){ Text(text = "Cancel Order?") }
//        TextButton(onClick = {  }){ Text(text = "Are You Sure, you want to cancel te order?") }