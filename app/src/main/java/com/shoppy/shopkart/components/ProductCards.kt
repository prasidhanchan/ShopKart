package com.shoppy.shopkart.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.Icon
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.shoppy.shopkart.R
import com.shoppy.shopkart.models.MProducts
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.ui.theme.roboto
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.DecimalFormat

@Composable
fun ProductCard(
    cardItem: List<MProducts>,
    navController: NavController,
    onAddToCartClick: (MProducts) -> Unit
) {
    LazyRow(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp)) {
        items(items = cardItem) { mProduct ->
            CardItem(
                mProducts = mProduct,
                navController = navController,
                onAddToCartClick = onAddToCartClick
            )
        }
    }
}

@Composable
fun CardItem(
    mProducts: MProducts,
    navController: NavController,
    onAddToCartClick: (MProducts) -> Unit
) {
    //encoding url because / in url leads to problems
    val encodedUrl =
        URLEncoder.encode(mProducts.product_url.toString(), StandardCharsets.UTF_8.toString())
    val encodedDescription = URLEncoder.encode(
        mProducts.product_description.toString(),
        StandardCharsets.UTF_8.toString()
    )
    //replacing + with a space
    val decodedDescription = encodedDescription.replace(oldValue = "+", newValue = " ")

    val encodedTitle =
        URLEncoder.encode(mProducts.product_title.toString(), StandardCharsets.UTF_8.toString())
    val decodedTitle = encodedTitle.replace(oldValue = "+", newValue = " ")

    Card(
        modifier = Modifier
            .width(165.dp)
            .height(202.dp)
            .padding(end = 10.dp),
        elevation = 0.dp,
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable { navController.navigate(BottomNavScreens.Details.route + "/${encodedUrl}/${decodedTitle}/${decodedDescription}/${mProducts.product_price}/${mProducts.stock}/${mProducts.category}/${mProducts.product_id}") },
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.height(115.dp)) {
                AsyncImage(
                    model = mProducts.product_url, contentDescription = mProducts.product_title,
                    modifier = Modifier.padding(8.dp),
                    placeholder = painterResource(id = R.drawable.placeholder)
                )
            }

            Text(
                text = mProducts.product_title!!,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = roboto
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .padding(start = 12.dp, end = 10.dp)
                    .align(Alignment.Start)
            )

            Text(
                text = mProducts.product_description!!,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = roboto
                ),
                overflow = TextOverflow.Ellipsis,
                color = Color.Black.copy(alpha = 0.5f),
                maxLines = 1,
                modifier = Modifier
                    .padding(bottom = 4.dp, start = 12.dp, end = 10.dp)
                    .align(Alignment.Start)
            )

            Text(
                text = "Price", style = TextStyle(fontSize = 10.sp, fontFamily = roboto),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 12.dp)
            )

            Text(
                text = "₹${DecimalFormat("#,##,###").format(mProducts.product_price!!.toDouble())}",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = roboto
                ),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 12.dp)
            )
        }
        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.TopEnd
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_cart),
                contentDescription = "Add to cart",
                tint = if (mProducts.stock!! > 0) Color.Black else Color.LightGray,
                modifier = Modifier
                    .size(20.dp)
                    .clickable(
                        enabled = mProducts.stock!! > 0,
                        onClick = { onAddToCartClick(mProducts) },
                    )
            )
        }
    }
}

@Preview
@Composable
private fun Previeww() {
    ProductCard(
        cardItem = listOf(
            MProducts(
                product_title = "POCO F4",
                product_description = "POCO F4 Green 6/128GB",
                product_price = 30000
            )
        ),
        navController = rememberNavController(),
        onAddToCartClick = { }
    )
}