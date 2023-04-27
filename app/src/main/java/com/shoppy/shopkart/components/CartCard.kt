package com.shoppy.shopkart.components

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.shoppy.shopkart.R
import com.shoppy.shopkart.models.MCart
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.screens.cart.CartScreenViewModel

@Composable
fun CartCard(cardList: List<MCart>,viewModel: CartScreenViewModel,
    navController: NavController
             ,priceLists: (MutableList<String>) -> Unit
){

    val priceList: MutableList<String> = mutableListOf()

    priceLists(priceList)

//    var priceLists = emptyList<Int>()

    LazyColumn(modifier = Modifier.padding(bottom = 10.dp)){
        items(items = cardList){mCart ->
            CartCardItem(mCart = mCart, viewModel = viewModel,
                navController = navController, priceList = {
                        price -> priceList.add(price)
                    Log.d("PRICEE", "CartCard: $price")
                }
            )
        }
    }
}

@Composable
fun CartCardItem(mCart: MCart,viewModel: CartScreenViewModel,
    navController: NavController,priceList: (String) -> Unit
){

    val countState = remember {
        mutableStateOf(1)
    }

    viewModel.updateCounter(updatedVal = countState.value, productTitle = mCart.product_title!!)

    priceList(mCart.product_price!!)

    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(140.dp)
        .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 2.dp
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
                        .padding(top = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top) {

                        Text(
                            text = mCart.product_title!!,
                            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold), overflow = TextOverflow.Ellipsis, maxLines = 1,
                            modifier = Modifier.width(200.dp)
                        )

                        Icon(imageVector = Icons.Rounded.Delete, contentDescription = "Delete Item", modifier = Modifier
                            .size(25.dp)
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
                        style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Light), overflow = TextOverflow.Ellipsis, maxLines = 1,
                        modifier = Modifier.width(180.dp))

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(top = 8.dp, bottom = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            text = "₹${mCart.product_price!!}",
                            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        )
                        
                        Surface(modifier = Modifier
                            .height(60.dp)
                            .width(100.dp),
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFECE2E2)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {

                                val enabled = countState.value > 1

                                PlusMinusButtons(icon = R.drawable.remove, desc = "Minus", enabled = enabled){countState.value-=1}
                                Text(text = countState.value.toString(), style = TextStyle(fontWeight = FontWeight.Bold))
                                PlusMinusButtons(icon = R.drawable.add, desc = "Add", enabled = true){countState.value+=1}

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

//@Preview
//@Composable
//fun Pre(){
//    CartCard(productUrl = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
//        productTitle = "MacBook Pro", productDescription = "MacBook Pro 2023", productPrice = "1,20,000")
//}