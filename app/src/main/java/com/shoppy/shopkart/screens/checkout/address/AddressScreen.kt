package com.shoppy.shopkart.screens.checkout.address

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.BackButton
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.components.ProgressBox
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.navigation.NavScreens
import com.shoppy.shopkart.ui.theme.roboto

@Composable
fun AddressScreen(navController: NavController,viewModel: AddressViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){

    val address = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }

    val context = LocalContext.current

    viewModel.getAddressNamePhone(name = {name.value = it}, phone = {phone.value = it}) {
        address.value = it
    }

    Scaffold(topBar = { BackButton(navController = navController, topBarTitle = "Address")}, backgroundColor = ShopKartUtils.offWhite) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Surface(modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
//                .padding(top = 0.dp)
                , elevation = 2.dp,
            color = Color.White) {

                Row(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {

                    ProgressBox(number = "1", title = "Address", color = ShopKartUtils.blue)
                    Divider(modifier = Modifier
                        .height(2.dp)
                        .width(40.dp))
                    ProgressBox(number = "2", title = "Order Summary",color = Color.Gray)
                    Divider(modifier = Modifier
                        .height(2.dp)
                        .width(40.dp))
                    ProgressBox(number = "3", title = "Payment",color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Surface(modifier = Modifier
                .fillMaxWidth()
                .height(185.dp)
                .padding(8.dp),
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                color = Color.White) {

                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start) {

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {

                        Icon(imageVector = Icons.Rounded.LocationOn, contentDescription = "Deliver To")

                        Text(text = "Deliver To", style = TextStyle(fontFamily = roboto))

                        Spacer(modifier = Modifier.width(200.dp))

                        IconButton(onClick = { navController.navigate(BottomNavScreens.EditAddressScreen.route)}) {
                            Text(text = "Edit")
                        }
                    }

                    Text(text = name.value, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = roboto),modifier = Modifier.padding(start = 12.dp))
                    Text(text = address.value, maxLines = 3, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(start = 12.dp,bottom = 12.dp), style = TextStyle(fontFamily = roboto))
                    Text(text = "+91 ${phone.value}",modifier = Modifier.padding(start = 12.dp), style = TextStyle(fontFamily = roboto))
                }
            }

            PillButton(title = "Continue", color = ShopKartUtils.black.toInt(), modifier = Modifier.padding(top = 10.dp)){
                if (address.value.isEmpty()) {
                    Toast.makeText(context, "Add Address", Toast.LENGTH_LONG).show()
                }else if (phone.value.isEmpty()){
                    Toast.makeText(context,"Add Phone number",Toast.LENGTH_LONG).show()
                }else{
                navController.navigate(BottomNavScreens.OrderSummaryScreen.route) }
            }
        }
    }
}



@Preview
@Composable
fun Pre(){
//    AddressScreen(navController = rememberNavController())
}