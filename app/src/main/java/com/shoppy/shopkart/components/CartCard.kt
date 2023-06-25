package com.shoppy.shopkart.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import com.shoppy.shopkart.models.MCart
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.screens.cart.CartScreenViewModel
import com.shoppy.shopkart.ui.theme.roboto
import java.text.DecimalFormat

@Composable
fun CartCard(cardList: List<MCart>,viewModel: CartScreenViewModel,
    navController: NavController,
             priceLists: (Int) -> Unit
){

    //List of price []
    val priceList: MutableList<Int> = mutableListOf()

    //adding values
    viewModel.sumValues(priceList){ priceLists(it) }

    //Works but does not load below items and the price is not added

//    LazyColumn(modifier = Modifier.padding(bottom = 10.dp)){
//        items(items = cardList){mCart ->
//            CartCardItem(mCart = mCart, viewModel = viewModel,
//                navController = navController, price = { price -> priceList.add(price)
//                }
//            )
//        }
//    }

    Column(modifier = Modifier
        .padding(bottom = 10.dp)
        .verticalScroll(rememberScrollState())) {


        for (card in cardList){
            CartCardItem(mCart = card, viewModel = viewModel, navController = navController, price = {price -> priceList.add(price) })
        }

    }
}

@Composable
fun CartCardItem(mCart: MCart,viewModel: CartScreenViewModel,
    navController: NavController,price: (Int) -> Unit
){

    val countState = remember { mutableStateOf(mCart.item_count) }

    val context = LocalContext.current

    val isInStock = mCart.stock!! > mCart.item_count!!

    //updating new counter value to firebase
    viewModel.updateCounter(updatedVal = countState.value!!, productTitle = mCart.product_title!!)

    //Adding item price * item count to priceList
    price(mCart.product_price!! * countState.value!!)

    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(140.dp)
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

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(top = 15.dp, end = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top) {

                        Text(
                            text = mCart.product_title!!,
                            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = roboto), overflow = TextOverflow.Ellipsis, maxLines = 1,
                            modifier = Modifier.width(160.dp)
                        )

                        Icon(painter = painterResource(id = R.drawable.delete), contentDescription = "Delete Item", modifier = Modifier
//                            .size(25.dp)
                            .clickable {

                                //deleting item from cart using product's title
                                viewModel.deleteItem(productTitle = mCart.product_title!!)

                                //navigating again to cart screen to refresh cart list
                                navController.popBackStack()
                                navController.navigate(BottomNavScreens.Cart.route)

                            })

                    }

                    Text(
                        text = mCart.product_description!!,
                        style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal, fontFamily = roboto), overflow = TextOverflow.Ellipsis, maxLines = 1,
                        modifier = Modifier.width(180.dp))

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(top = 8.dp, bottom = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            text = "₹${DecimalFormat("#,##,###").format(mCart.product_price!!.toDouble())}",
                            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        )
                        
                        Surface(modifier = Modifier
                            .height(60.dp)
                            .width(100.dp),
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFECE2E2)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {

                                val enabledPlus = countState.value!! <= 4
                                val enabledMinus = countState.value!! > 1

                                PlusMinusButtons(icon = R.drawable.remove, desc = "Minus", enabled = enabledMinus){
                                    countState.value = countState.value!! - 1

                                    navController.popBackStack()
                                    navController.navigate(BottomNavScreens.Cart.route)
                                }
                                Text(text = countState.value.toString(), style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = roboto))
                                PlusMinusButtons(icon = R.drawable.add, desc = "Add", enabled = enabledPlus && isInStock){
                                    countState.value = countState.value!! + 1

                                    if (countState.value!! == 5) Toast.makeText(context,"Maximum quantity per item reached",Toast.LENGTH_SHORT).show()

                                    if (mCart.stock!! == countState.value!!) Toast.makeText(context,"Only ${mCart.stock} left in stock",Toast.LENGTH_SHORT).show()

                                    navController.popBackStack()
                                    navController.navigate(BottomNavScreens.Cart.route)
                                }

                            }
                        }
                    }
                }

            }
        }
}

@Composable
fun PlusMinusButtons(modifier: Modifier = Modifier,icon: Int,desc: String?,enabled: Boolean,onClick: () -> Unit) {

    Surface(modifier = Modifier
        .size(28.dp)
        .clip(RoundedCornerShape(5.dp))
    ){
    IconButton(modifier = modifier
        .background(Color.White)
        .padding(1.dp)
        .clip(RoundedCornerShape(5.dp)),
        enabled = enabled, onClick = {onClick.invoke()}) {
            Icon(painter = painterResource(id = icon), contentDescription = desc)
        }
    }
}