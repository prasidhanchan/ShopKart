package com.shoppy.shopkart.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shoppy.shopkart.R
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.screens.details.DetailsScreenViewModel
import com.shoppy.shopkart.ui.theme.roboto


@Composable
fun BackButton(modifier: Modifier = Modifier, navController: NavController, viewModel: DetailsScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),category: String = "", productId: String = "", topBarTitle: String = "", spacing: Dp = 80.dp) {

    val email = remember { mutableStateOf("") }
    email.value = viewModel.emailId.toString()

    val context = LocalContext.current

    val topBarArrangement = if (topBarTitle == "Details") Arrangement.SpaceEvenly else Arrangement.Start
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 30.dp, top = 30.dp, bottom = 8.dp),
        horizontalArrangement = topBarArrangement
    ) {

        Surface(
            modifier = Modifier
                .size(50.dp),
            shape = CircleShape,
        ) {

            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { navController.popBackStack() },
                contentAlignment = Alignment.Center
            ) {

                Image(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back",
                    modifier = Modifier)
            }
        }
        
        Spacer(modifier = Modifier.width(spacing))

        Text(text = topBarTitle, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, fontFamily = roboto), modifier = Modifier.padding(top = 10.dp))

        if (topBarTitle == "Details"){
            if (email.value.contains("admin.") || email.value.contains("employee.")){

                Spacer(modifier = Modifier.width(80.dp))

                Icon(painter = painterResource(id = R.drawable.delete), contentDescription = "Delete Product", tint = Color.Black,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 15.dp)
                        .clickable {
                            viewModel.deleteProduct(category = category, productId = productId)
                            navController.popBackStack() //this popBackStack is to pop Details Screen
                            navController.popBackStack() //this popBackStack is to pop Previous Home Screen
                            navController.popBackStack()
                            navController.navigate(BottomNavScreens.Home.route)
                            Toast.makeText(context,"Product Removed",Toast.LENGTH_SHORT).show()
                        })
            }else {

                Spacer(modifier = Modifier.width(80.dp))

                Icon(painter = painterResource(id = R.drawable.ic_cart), contentDescription = "Go to Cart", tint = Color.Black,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 15.dp)
                        .clickable { navController.navigate(BottomNavScreens.Cart.route) })
            }
        }else{
            Box{}
        }
    }
}