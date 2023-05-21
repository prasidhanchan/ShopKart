package com.shoppy.shopkart.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.models.MOrder
import com.shoppy.shopkart.screens.admin.orderstatus.OrderStatusViewModel
import com.shoppy.shopkart.screens.employee.orderstatus.OrderStatusEmpViewModel
import com.shoppy.shopkart.ui.theme.roboto
import java.text.DecimalFormat

@Composable
fun DeliveryStatusCard(ordered: MOrder,buttonTitle: String, navHostController: NavHostController, viewModel: OrderStatusViewModel,buttonClick:() -> Unit = { }) {

    //If button text is "Item Delivered" button is disabled else enabled
    val isEnabled = when(buttonTitle){
        "Item Delivered" -> false
        else -> true
    }

    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .padding(10.dp),
        shape = RoundedCornerShape(12.dp)
    ) {

        val context = LocalContext.current

        Row(modifier = Modifier.fillMaxSize().padding(5.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {

            AsyncImage(model = ordered.product_url, contentDescription = ordered.product_title)

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = ordered.product_title!!,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = roboto)
                )
                Text(
                    text = "₹${DecimalFormat("#,##,###").format(ordered.product_price?.toDouble())}",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = roboto)
                )
            }

//            Button(onClick = { /*TODO*/ }, shape = RoundedCornerShape(12.dp), modifier = Modifier
//                .height(60.dp)
//                .width(160.dp)) {
//                Text(text = "Mark On The Way", style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = roboto))
//            }

//            if (buttonTitle == "Mark On The Way" || buttonTitle == "Mark Delivered") {
                PillButton(
                    title = buttonTitle,
                    color = ShopKartUtils.black.toInt(),
                    textSize = 12,
                    modifier = Modifier
                        .height(50.dp)
                        .width(160.dp),
                    enabled = isEnabled
                ) {
                    buttonClick.invoke()
                }
        }

    }
}

@Composable
fun DeliveryStatusCardForEmp(ordered: MOrder,buttonTitle: String, navHostController: NavHostController, viewModel: OrderStatusEmpViewModel,buttonClick:() -> Unit = { }) {

    //If button text is "Item Delivered" button is disabled else enabled
    val isEnabled = when(buttonTitle){
        "Item Delivered" -> false
        else -> true
    }

    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .padding(10.dp),
        shape = RoundedCornerShape(12.dp)
    ) {

        val context = LocalContext.current

        Row(modifier = Modifier.fillMaxSize().padding(5.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {

            AsyncImage(model = ordered.product_url, contentDescription = ordered.product_title)

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = ordered.product_title!!,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = roboto)
                )
                Text(
                    text = "₹${DecimalFormat("#,##,###").format(ordered.product_price?.toDouble())}",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = roboto)
                )
            }

//            Button(onClick = { /*TODO*/ }, shape = RoundedCornerShape(12.dp), modifier = Modifier
//                .height(60.dp)
//                .width(160.dp)) {
//                Text(text = "Mark On The Way", style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = roboto))
//            }

//            if (buttonTitle == "Mark On The Way" || buttonTitle == "Mark Delivered") {
            PillButton(
                title = buttonTitle,
                color = ShopKartUtils.black.toInt(),
                textSize = 12,
                modifier = Modifier
                    .height(50.dp)
                    .width(160.dp),
                enabled = isEnabled
            ) {
                buttonClick.invoke()
            }
        }

    }
}