package com.shoppy.shopkart.screens.details

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.BackButton
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.ui.theme.roboto
import java.text.DecimalFormat

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    navController: NavController, viewModel: DetailsScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    imageUrl:Any?,
    productTitle: String = "",
    productDescription: String = "",
    productPrice: Int = 0) {

    val context = LocalContext.current

    val urlState = remember { mutableStateOf(imageUrl) }
    val titleState = remember { mutableStateOf(productTitle) }
    val descriptionState = remember { mutableStateOf(productDescription) }
    val priceState = remember { mutableStateOf(productPrice) }


    Scaffold(
        topBar = {
            //Back Button
            BackButton(navController = navController)
        },
//        bottomBar = { DetailsBottomBar(
//            viewModel = viewModel,
//            url = urlState.value,
//            title = titleState.value,
//            description = descriptionState.value,
//            price = priceState.value
//        ) },
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = ShopKartUtils.offWhite
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Surface(
                modifier = Modifier
                    .height(350.dp)
                    .width(340.dp)
                    .clip(RoundedCornerShape(20.dp))
//                .padding(20.dp)

            ) {

                AsyncImage(
                    model = imageUrl, contentDescription = productTitle,
                    placeholder = painterResource(R.drawable.placeholder),
                    modifier = Modifier.padding(5.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
//                    .height(230.dp)
                    .padding(start = 15.dp, end = 15.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = productTitle,
                    style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, fontFamily = roboto),
                    modifier = Modifier.padding(top = 22.dp)
                )

                Text(
                    text = "Description : ",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = roboto),
                    modifier = Modifier.padding(top = 12.dp))

                Text(
                    text = productDescription,
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal, fontFamily = roboto, color = Color.Black.copy(alpha = 0.5f)),
                    maxLines = 10, overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.height(135.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(top = 10.dp)
                ) {

                    Text(buildAnnotatedString {
                        Text(
                            text = "Price : ",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontFamily = roboto
                            )
                        )
                        Text(
                            text = "₹${DecimalFormat("#,##,###").format(priceState.value.toDouble())}",
                            style = TextStyle(
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = roboto
                            )
                        )
                    })

                }

                PillButton(
                    title = "Add to cart", color = ShopKartUtils.black.toInt(), shape = 16.dp,
                    modifier = Modifier.padding(top = 10.dp)
                ) {

                    //Uploading Item to Firebase Cart
                    viewModel.uploadCartToFirebase(
                        url = urlState.value,
                        title = titleState.value,
                        description = descriptionState.value,
                        price = priceState.value
                    )
                    Toast.makeText(context, "Item added to cart", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}

@Composable
fun DetailsBottomBar(viewModel: DetailsScreenViewModel, url: Any?,title: String,description: String,price: Int){

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        ) {

            Text(buildAnnotatedString {
                Text(
                    text = "Price : ",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = roboto),
                    modifier = Modifier.padding(start = 35.dp)
                )
                Text(
                    text = "₹${DecimalFormat("#,##,###").format(price.toDouble())}",
                    style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, fontFamily = roboto)
                )
            })

        }

        PillButton(
            title = "Add to cart", color = ShopKartUtils.black.toInt(), shape = 16.dp,
            modifier = Modifier.padding(bottom = 100.dp)
        ) {

            //Uploading Item to Firebase Cart
            viewModel.uploadCartToFirebase(url = url,
                title = title,
                description = description,
                price = price)
            Toast.makeText(context, "Item added to cart", Toast.LENGTH_SHORT).show()

        }

//                Spacer(modifier = Modifier.height(120.dp))

    }
}

//@Preview
//@Composable
//fun Pre(){
//    val navController = rememberNavController()
//    DetailsScreen(navController = navController,"","MacBook Pro","abcdefghasdfghjklertnmdfm","2,00,000")
//}