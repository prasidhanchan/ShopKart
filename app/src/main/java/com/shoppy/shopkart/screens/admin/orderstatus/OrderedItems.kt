package com.shoppy.shopkart.screens.admin.orderstatus

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.BackButton
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.models.MOrder
import com.shoppy.shopkart.screens.search.SearchBox
import com.shoppy.shopkart.ui.theme.roboto
import java.text.DecimalFormat

@Composable
fun OrderedItems(navHostController: NavHostController,viewModel: OrderStatusViewModel = hiltViewModel()){

//    var orderedItemsList = emptyList<MOrder>()
    var orderedItemsList = remember { mutableStateOf(emptyList<MOrder>()) }
    Log.d("OrderedList", "OrderedItems: $orderedItemsList")

    val searchByOrderId = remember { mutableStateOf("") }

    orderedItemsList.value = viewModel.fireStatus.value.data?.toList()?.filter { mOrder ->

//        mOrder.order_id == searchByOrderId.value
        mOrder.delivery_status == "Ordered"

    }!!

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = { BackButton(navController = navHostController, topBarTitle = "Ordered Items") }, backgroundColor = ShopKartUtils.offWhite) { innerPadding ->

        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {

                SearchBox(value = searchByOrderId.value, onChange = searchByOrderId, leadingIcon = R.drawable.ic_search, placeHolder = "Search by Order Id")
                //Search Button
                IconButton(modifier = Modifier
                    .size(55.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Black),
                    onClick = {
                        orderedItemsList.value = viewModel.fireStatus.value.data?.toList()?.filter { mOrder ->
                        mOrder.order_id == searchByOrderId.value
                    }!!
                    }){
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }
            }

            LazyColumn{
                items(items = orderedItemsList.value){ ordered ->
                    OrderedStatusCard(ordered = ordered, navHostController = navHostController,viewModel = viewModel)
                }
            }

        }
    }
}

@Composable
fun OrderedStatusCard(ordered: MOrder,navHostController: NavHostController,viewModel: OrderStatusViewModel) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .padding(10.dp),
        shape = RoundedCornerShape(12.dp)
    ) {

        val context = LocalContext.current

        Row(modifier = Modifier.fillMaxSize().padding(5.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {

            AsyncImage(model = ordered.product_url, contentDescription = ordered.product_title)

            Column(modifier = Modifier
                .fillMaxHeight()
                .width(100.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {

                Text(text = ordered.product_title!!, style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = roboto))
                Text(text = "₹${ DecimalFormat("#,##,###").format(ordered.product_price?.toDouble()) }", style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = roboto))
            }

//            Button(onClick = { /*TODO*/ }, shape = RoundedCornerShape(12.dp), modifier = Modifier
//                .height(60.dp)
//                .width(160.dp)) {
//                Text(text = "Mark On The Way", style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = roboto))
//            }

            PillButton(title = "Mark On The Way", color = ShopKartUtils.black.toInt(), textSize = 12, modifier = Modifier
                .height(50.dp)
                .width(160.dp)){ viewModel.markOnTheWay(userId = ordered.user_id!!, product_title = ordered.product_title!!){
                navHostController.popBackStack()
                Toast.makeText(context,"Item marked as On The Way",Toast.LENGTH_SHORT).show() } }
        }

    }
}
