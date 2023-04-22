package com.shoppy.shopkart.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shoppy.shopkart.R
import com.shoppy.shopkart.models.MProducts

@Composable
fun ProductCard(cardItem:List<MProducts>,onClick: () -> Unit){

    LazyRow(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp)){
        items(items = cardItem){mProduct ->
            CardItem(mProducts = mProduct) { onClick.invoke() }
        }
    }
}

@Composable
fun CardItem(mProducts: MProducts,onClick: () -> Unit) {
    Card(modifier = Modifier
        .width(165.dp)
        .height(215.dp)
        .padding(start = 5.dp, end = 5.dp),
        elevation = 0.dp,
        backgroundColor = Color(0xFFE0ECEA),
    shape = RoundedCornerShape(15.dp)
    ) {

        Column(modifier = Modifier
            .fillMaxSize()
            .clickable { onClick.invoke() }) {

            Surface(modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 5.dp),
                shape = RoundedCornerShape(15.dp)
            ) {
                Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {

//                    Box(modifier = Modifier.size(115.dp)) {

                        AsyncImage(model = mProducts.product_url, contentDescription = mProducts.product_title,
                            modifier = Modifier.padding(8.dp),
                            placeholder = painterResource(
                                id = R.drawable.placeholder))
//                    }
                }

            }

            Column(verticalArrangement = Arrangement.Top) {

                Text(text = mProducts.product_title!!, style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .align(Alignment.Start))

                Text(text = mProducts.product_description!!, style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold ),
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black.copy(alpha = 0.5f),
                    maxLines = 1,
                    modifier = Modifier
                        .padding(bottom = 4.dp, start = 12.dp)
                        .align(Alignment.Start))

                Text(text = "Price", style = TextStyle(fontSize = 10.sp),
                    modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 12.dp))

                Text(text = "₹${ mProducts.product_price!! }", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.ExtraBold ),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 12.dp))
            }
        }

    }
}
@Preview
@Composable
fun Pre(){
    ProductCard(listOf(MProducts("","MacBook","1000","MacBook Pro Special Edition"))){}
}