package com.shoppy.shopkart.screens.checkout.address

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.BackButton
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.components.ProgressBox
import com.shoppy.shopkart.components.TextBox
import com.shoppy.shopkart.components.TextBox3
import com.shoppy.shopkart.ui.theme.roboto

@Composable
fun EditAddressScreen(navController: NavHostController,viewModel: AddressViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val nameState = remember { mutableStateOf("") }
    val addressState = remember { mutableStateOf("") }
    val phoneState = remember { mutableStateOf("") }
    val errorState = remember { mutableStateOf("") }

    val context = LocalContext.current

    viewModel.getAddressNamePhone(name = {nameState.value = it}, phone = {phoneState.value = it}) {
        addressState.value = it
    }

    Scaffold(topBar = { BackButton(navController = navController, topBarTitle = "Edit Address")}, backgroundColor = ShopKartUtils.offWhite) { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                elevation = 2.dp,
                color = Color.White
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    ProgressBox(number = "1", title = "Address", color = ShopKartUtils.blue)
                    Divider(
                        modifier = Modifier
                            .height(2.dp)
                            .width(50.dp)
                    )
                    ProgressBox(number = "2", title = "Order Summary", color = Color.Gray)
                    Divider(
                        modifier = Modifier
                            .height(2.dp)
                            .width(50.dp)
                    )
                    ProgressBox(number = "3", title = "Payment", color = Color.Gray)
                }
            }

            TextBox3(label = "Name", value = nameState.value, onChange = nameState, modifier = Modifier.padding(top = 20.dp))
            TextBox3(label = "Address", value = addressState.value, onChange = addressState, isSingleLine = false)
            TextBox3(label = "Phone no", value = phoneState.value, onChange = phoneState, keyBoardType = KeyboardType.Number, imeAction = ImeAction.Done)
            Text(text = errorState.value, style = TextStyle(fontSize = 15.sp, fontFamily = roboto, fontWeight = FontWeight.Bold), color = Color.Red)

            PillButton(title = "Update Address", color = ShopKartUtils.black.toInt(), modifier = Modifier.padding(top = 12.dp)){

                if (phoneState.value.length > 10 || phoneState.value.length <= 9){
                   errorState.value = "Enter a valid number"
                }else{
                    viewModel.updateAddress(name = nameState.value,address = addressState.value, phone = phoneState.value)
                    navController.popBackStack()
                    Toast.makeText(context,"Address Updated",Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}