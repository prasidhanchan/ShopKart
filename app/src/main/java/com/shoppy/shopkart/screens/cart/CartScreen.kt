package com.shoppy.shopkart.screens.cart

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.CartCard
import com.shoppy.shopkart.components.LoadingComp
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.models.MCart
import com.shoppy.shopkart.ui.theme.roboto
import java.text.DecimalFormat

@Composable
fun CartScreen(navController: NavController, viewModel: CartScreenViewModel = hiltViewModel(),naviAddress:() -> Unit){

    var cartList = emptyList<MCart>()

    val userId = FirebaseAuth.getInstance().currentUser?.uid

    val totalAmount = remember { mutableStateOf(0) }

//    var priceList = emptyList<Int>()
//    var priceList: MutableList<Int> = mutableListOf()

//    viewModel.getPrice(){
//        priceList = listOf(it)
//    }

    if (!viewModel.fireCart.value.data.isNullOrEmpty()){

        //filtering cart according to user by comparing current user Id with the user_id field in firebase Cart
        cartList = viewModel.fireCart.value.data!!.toList().filter { mCart ->
            mCart.user_id == userId
        }
    }


    Scaffold(topBar = {CartAppBar()}, backgroundColor = ShopKartUtils.offWhite) { innerPadding ->

        if (!viewModel.fireCart.value.loading!!) {

//            Log.d("PRICELIST", "CartScreen: ${priceLists.value}")

                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth()
                        .height(530.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CartCard(
                        cardList = cartList, viewModel = viewModel, navController = navController
                    )
                    { price ->
//                        viewModel.sumValues(price){ totalAmount.value = it
//                            Log.d("ITTT", "CartScreen: $it")
//                        }
//                        Log.d("CartScreen", "CartScreen: $price")
                        totalAmount.value = price
//                        Log.d("PRICELIST", "CartScreen: ${listOf(it)}")
//                        Log.d("PRICELISTS", "CartScreen: ${priceList}")
//                        Log.d("PRICE", "CartScreen: ${Sum.FIELD_FIELD_NUMBER}")
//                        priceList = listOf(it)

//                        priceList.add(price)
                    }
                }

            //if cart is not empty show price and button else empty cart logo
            if (cartList.isNotEmpty()) CartBottomBar(totalAmount = totalAmount.value.toString(), navigateAddress = {naviAddress.invoke()}) else Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                painter = painterResource(id = R.drawable.empty_cart), contentScale = ContentScale.Crop,
                contentDescription = "Yor Cart Is Empty", modifier = Modifier
                        .size(300.dp)
                        .padding(bottom = 10.dp)
                        .clip(CircleShape))

                Text(text = "Your Cart Is Empty\n  Add Something!", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = roboto))
            }

        }else{
            LoadingComp()
        }

    }

}

@Composable
fun CartAppBar(){
    Column(verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)) {
            Text(text="My Cart",
                style= TextStyle(fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, fontFamily = roboto))
        Divider(modifier = Modifier
            .height(2.dp)
            .width(320.dp))
    }
}

@Composable
fun CartBottomBar(totalAmount: String,navigateAddress:() -> Unit){

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Total Price",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = roboto),
                modifier = Modifier.padding(start = 15.dp)
            )

            Spacer(modifier = Modifier.width(100.dp))

                Text(
                    text = "₹${DecimalFormat("#,##,###").format(totalAmount.toDouble())}",
                    style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.ExtraBold, fontFamily = roboto),
                    modifier = Modifier.padding(end = 15.dp)
                )

        }

        Spacer(modifier = Modifier.height(20.dp))

        PillButton(title = "Check Out", color = ShopKartUtils.black.toInt()) {
            navigateAddress()
        }

        Spacer(modifier = Modifier.height(98.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun Prev(){
//    CartBottomBar()
}