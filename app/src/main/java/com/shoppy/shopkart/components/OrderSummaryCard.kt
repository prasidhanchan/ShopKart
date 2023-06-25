package com.shoppy.shopkart.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shoppy.shopkart.R
import com.shoppy.shopkart.models.MCart
import com.shoppy.shopkart.screens.checkout.ordersummary.OrderSummaryScreenViewModel
import com.shoppy.shopkart.ui.theme.roboto
import java.text.DecimalFormat

@Composable
fun OrderSummaryCard(cardList: List<MCart>,viewModel: OrderSummaryScreenViewModel,modifier: Modifier = Modifier,
             priceLists: (Int) -> Unit
){

    val priceList: MutableList<Int> = mutableListOf()

    //adding values
    viewModel.sumValues(priceList){ priceLists(it) }

//    LazyColumn(modifier = modifier){
//        items(items = cardList){ mCart ->
//
//            OrderSummaryItem(mCart = mCart, price = { price -> priceList.add(price) })
//
//        }
//    }

    Column(modifier = modifier
        .padding(bottom = 10.dp)
        .verticalScroll(rememberScrollState())) {

    for (card in cardList){
            OrderSummaryItem(mCart = card, price = { price -> priceList.add(price) })
        }
    }
}

@Composable
fun OrderSummaryItem(mCart: MCart,price: (Int) -> Unit
){

    val countState = remember { mutableStateOf(mCart.item_count) }

    price(mCart.product_price!! * countState.value!!)

    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)
        .padding(start = 20.dp, end = 20.dp, top = 5.dp, bottom = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {

        Row(modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {

            Box(modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .width(100.dp)
                .fillMaxHeight()
                .padding(start = 10.dp, top = 18.dp, bottom = 8.dp)) {

                AsyncImage(model = mCart.product_url, contentDescription = mCart.product_title,
                    placeholder = painterResource(id = R.drawable.placeholder), contentScale = ContentScale.Inside)
            }


            Column(modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 25.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                    Text(
                        text = mCart.product_title!!,
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = roboto), overflow = TextOverflow.Ellipsis, maxLines = 1,
                        modifier = Modifier.width(200.dp)
                    )

                Text(
                    text = mCart.product_description!!,
                    style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal, fontFamily = roboto), overflow = TextOverflow.Ellipsis, maxLines = 1,
                    modifier = Modifier.width(180.dp))

                    Text(
                        text = "₹${DecimalFormat("#,##,###").format(mCart.product_price.toString().toDouble())}",
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = roboto), modifier = Modifier.padding(top = 8.dp)
                    )
            }
        }
    }
}