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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
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
            BackButton(navController = navController, topBarTitle = "Details")
        },
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
//                .verticalScroll(rememberScrollState())
        ) {
            Surface(
                modifier = Modifier
                    .height(290.dp)
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
                    .fillMaxWidth()
                    .height(215.dp)
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
                    text = productDescription,
                    style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Normal, fontFamily = roboto),
                    modifier = Modifier.padding(top = 12.dp)
                )

            }
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
                        .height(50.dp)
                ) {

                    Text(buildAnnotatedString {
                        Text(
                            text = "Price : ",
                            style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = roboto),
                            modifier = Modifier.padding(start = 15.dp)
                        )
                        Text(
                            text = "₹${DecimalFormat("#,##,###").format(productPrice.toDouble())}",
                            style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, fontFamily = roboto)
                        )
                    })

                }

                PillButton(
                    title = "Add to cart", color = ShopKartUtils.black.toInt(), shape = 16.dp,
                    modifier = Modifier.padding(bottom = 110.dp)
                ) {

                    viewModel.uploadCartToFirebase(url = urlState.value,
                        title = titleState.value,
                        description = descriptionState.value,
                        price = priceState.value)
                    Toast.makeText(context, "Item added to cart", Toast.LENGTH_SHORT).show()

                }

//                Spacer(modifier = Modifier.height(120.dp))

            }
        }
    }
}

//@Preview
//@Composable
//fun Pre(){
//    val navController = rememberNavController()
//    DetailsScreen(navController = navController,"","MacBook Pro","abcdefghasdfghjklertnmdfm","2,00,000")
//}