package com.shoppy.shopkart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.google.firebase.messaging.FirebaseMessaging
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.models.MOrder
import com.shoppy.shopkart.models.NotificationData
import com.shoppy.shopkart.models.PushNotificationData
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.notification.Notification
import com.shoppy.shopkart.screens.orders.MyOrderViewModel
import com.shoppy.shopkart.ui.theme.roboto
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.DecimalFormat


@Composable
fun OrdersCard(cardList: List<MOrder>,
             navController: NavController,
               viewModel: MyOrderViewModel
){

    val context = LocalContext.current

    val myNotification = Notification(context = context)

    val scope = rememberCoroutineScope()

    LazyColumn(contentPadding = PaddingValues(bottom = 100.dp)){
        items(items = cardList){ mOrders ->
            OrdersCardItem(mOrder = mOrders, navController = navController)
        }
    }

for (card in cardList){
    LaunchedEffect(key1 = card.delivery_status){

        if (card.notificationCount == 1 && card.delivery_status == "On The Way") {

            scope.launch {
                    myNotification.showNotification(
                        title = "On the way",
                        text = "Your ${card.product_title} is on the way and may arrive soon",
                        notificationImg = card.product_url!!
                    ).run {
//                        viewModel.incrementNotificationCount(productTitle = card.product_title!!)
                    }
            }

        }else if (card.notificationCount == 2 && card.delivery_status == "Delivered"){

            scope.launch {
                    myNotification.showNotification(
                        title = "Delivered",
                        text = "Your ${card.product_title} is Delivered to your address",
                        notificationImg = card.product_url!!
                    ).run {
                        viewModel.incrementNotificationCount(productTitle = card.product_title!!)
                    }
            }
        }
    }
}

//    FirebaseMessaging.getInstance().subscribeToTopic(ShopKartUtils.TOPIC)

  /*  for (card in cardList){
        when(card.delivery_status){
            "Delivered" -> PushNotificationData(
                data = NotificationData(title = "Delivered", message = "Your ${card.product_title} is delivered"),
                to = ShopKartUtils.TOPIC
            ).also {
                viewModel.sendNotification(it)
            }

            else -> PushNotificationData(
                data = NotificationData(title = "On The Way", message = "Your ${card.product_title} is on the way"),
                to = ShopKartUtils.TOPIC
            ).also {
                viewModel.sendNotification(it)
            }
        }
    } */

}

@Composable
fun OrdersCardItem(mOrder: MOrder, navController: NavController) {

    val countState = remember { mutableStateOf(mOrder.item_count) }


    val encodeUrl = URLEncoder.encode(mOrder.product_url.toString(),StandardCharsets.UTF_8.toString())

    val encodePaymentMethod = URLEncoder.encode(mOrder.payment_method.toString(),StandardCharsets.UTF_8.toString())
    val decodePaymentMethod = encodePaymentMethod.replace(oldValue = "+", newValue = " ")

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(start = 20.dp, end = 20.dp, top = 5.dp, bottom = 8.dp)
            .clickable { navController.navigate(BottomNavScreens.MyOrderDetails.route + "/${mOrder.delivery_status}/${mOrder.product_title}/${encodeUrl}/${mOrder.product_price}/${mOrder.item_count}/${decodePaymentMethod}/${mOrder.order_id}/${mOrder.order_date}") },
        shape = RoundedCornerShape(12.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .width(100.dp)
                    .fillMaxHeight()
                    .padding(start = 10.dp, top = 10.dp, bottom = 8.dp)
            ) {

                AsyncImage(
                    model = mOrder.product_url,
                    contentDescription = mOrder.product_title,
                    placeholder = painterResource(id = R.drawable.placeholder),
                    contentScale = ContentScale.Inside
                )
            }


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp, end = 25.dp, top = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = mOrder.product_title!!,
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = roboto),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.width(200.dp)
                )

                Text(
                    text = mOrder.product_description!!,
                    style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal, fontFamily = roboto),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.width(180.dp)
                )

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(top = 8.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = "₹${DecimalFormat("#,##,###").format(((mOrder.product_price!! - 100) / countState.value!!).toString().toDouble())}",
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = roboto),
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Spacer(modifier = Modifier.width(30.dp))

                    Text(
                        text = mOrder.delivery_status!!,
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = roboto),
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    //Changing logo as per delivery status
                    val logo = if (mOrder.delivery_status!! == "On The Way") R.drawable.on_the_way
                    else if (mOrder.delivery_status!! == "Delivered") R.drawable.delivered
                    else if (mOrder.delivery_status!! == "Cancelled") R.drawable.cancel
                    else R.drawable.ordered

                    val tint =  if (mOrder.delivery_status!! == "Cancelled") Color.Red else if (mOrder.delivery_status!! == "Delivered") Color(0xFFCDDC39) else if (mOrder.delivery_status!! == "On The Way") ShopKartUtils.blue else Color.Black

                    Icon(modifier = Modifier
                        .padding(start = 5.dp, top = 8.dp)
                        .size(25.dp), painter = painterResource(id = logo), contentDescription = "Delivery Status", tint = tint)
                }
            }
        }
    }
}