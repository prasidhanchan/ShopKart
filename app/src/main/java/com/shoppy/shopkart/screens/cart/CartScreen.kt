package com.shoppy.shopkart.screens.cart

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
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
import com.shoppy.shopkart.ShopKartColors
import com.shoppy.shopkart.components.CartCard
import com.shoppy.shopkart.components.LoadingComp
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.models.MCart

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CartScreen(navController: NavController, viewModel: CartScreenViewModel = hiltViewModel()){

    var cartList = emptyList<MCart>()

    val userId = FirebaseAuth.getInstance().currentUser?.uid

    val priceLists = remember {
        mutableStateOf(emptyList<String>())
    }

    var priceList = emptyList<String>()
    
//    viewModel.getPrice(){
//        priceList = listOf(it)
//    }

    if (!viewModel.fireCart.value.data.isNullOrEmpty()){

        //filtering cart according to user by comparing current user Id with the user_id field in firebase Cart
        cartList = viewModel.fireCart.value.data!!.toList().filter { mCart ->
            mCart.user_id == userId
        }
    }




    Scaffold(topBar = {CartAppBar()}, backgroundColor = ShopKartColors.offWhite) {

        if (!viewModel.fireCart.value.loading!!) {

//            Log.d("PRICELIST", "CartScreen: ${priceLists.value}")

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(530.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CartCard(
                        cardList = cartList, viewModel = viewModel, navController = navController
                    )
                    { price ->
//                        Log.d("PRICELIST", "CartScreen: ${listOf(it)}")
                        Log.d("PRICELISTS", "CartScreen: $price")
//                        priceList = listOf(it)

                        priceLists.value = price
                    }
                }

            //if cart is not empty show price and button else empty cart logo
            if (cartList.isNotEmpty()) CartBottomBar() else Column(modifier = Modifier.fillMaxSize().padding(top = 80.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                painter = painterResource(id = R.drawable.empty_cart),
                contentDescription = "Yor Cart Is Empty", modifier = Modifier.size(300.dp))

                Text(text = "Your Cart Is Empty\n  Add Something!", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
            }

        }else{
            LoadingComp()
        }

        
    }

}

@Composable
fun CartAppBar(){
    Column(verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)) {
            Text(text="Your Cart",
                style= TextStyle(fontSize = 22.sp, fontWeight = FontWeight.ExtraBold))
        Divider(modifier = Modifier.height(2.dp))
    }
}

@Composable
fun CartBottomBar(){

    val context = LocalContext.current

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
            verticalAlignment = Alignment.Top
        ) {

            Text(
                text = "Total Price:",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(start = 15.dp)
            )

            Spacer(modifier = Modifier.width(100.dp))

            Text(
                text = "₹20,00,000"
//                        "₹${totalPrice.value}"
                ,
                style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.ExtraBold),
                modifier = Modifier.padding(end = 15.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        PillButton(title = "Check Out", color = Color(0xFFCDDC39).toArgb(), textColor = Color.Black) {
            //TODO Payment Screen
            Toast.makeText(context,"Not Implemented",Toast.LENGTH_SHORT).show()
        }

        Spacer(modifier = Modifier.height(98.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun Prev(){
    CartBottomBar()
}