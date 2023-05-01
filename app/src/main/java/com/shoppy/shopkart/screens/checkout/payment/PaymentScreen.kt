package com.shoppy.shopkart.screens.checkout.payment

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonColors
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.BackButton
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.components.ProgressBox
import com.shoppy.shopkart.components.TextBox
import com.shoppy.shopkart.navigation.NavScreens
import com.shoppy.shopkart.screens.checkout.ordersummary.OrderSummaryScreenViewModel
import java.text.DecimalFormat

@Composable
fun PaymentScreen(itemsPrice: Int,navController: NavHostController,viewModel: OrderSummaryScreenViewModel = hiltViewModel()) {

    val options = listOf("COD", "Card")

    val selectedOption = remember { mutableStateOf(options[0]) }
    val cardHolder = remember { mutableStateOf("") }
    val creditCard = remember { mutableStateOf("4532804020291443") }
    val expiry = remember { mutableStateOf("5/2027") }
    val cvv = remember { mutableStateOf("633") }
    val isSelected = remember { mutableStateOf(false) }

    viewModel.getName{ cardHolder.value = it }

    val context = LocalContext.current

    Scaffold(topBar = { BackButton(navController = navController, topBarTitle = "Payment") },
        backgroundColor = ShopKartUtils.offWhite, bottomBar = { PaymentBottomBar() }) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Surface(modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
                elevation = 2.dp,
                color = Color.White) {

                Row(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {

                    ProgressBox(number = "1", title = "Address", color = Color.Blue)
                    Divider(modifier = Modifier
                        .height(2.dp)
                        .width(50.dp))
                    ProgressBox(number = "2", title = "Order Summary",color = Color.Blue)
                    Divider(modifier = Modifier
                        .height(2.dp)
                        .width(50.dp))
                    ProgressBox(number = "3", title = "Payment",color = Color.Blue)
                }
            }

            Card(modifier = Modifier
                .padding(10.dp)
                .height(100.dp)
                .width(350.dp), elevation = 2.dp,
                shape = RoundedCornerShape(12.dp)) {
                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start, modifier = Modifier
                    .background(Color.White)
                    .padding(start = 35.dp, end = 15.dp)) {

                    RowComp(title = "Items Price:", price = "₹${ DecimalFormat("#,##,###").format(itemsPrice.toDouble()) }", space = 120.dp)
                    RowComp(title = "Delivery Fee:", price = "₹${ DecimalFormat("#,##,###").format((100).toDouble()) }", space = 115.dp)
                    RowComp(title = "Total Price:", price = "₹${ DecimalFormat("#,##,###").format((itemsPrice + 100).toDouble()) }", space = 125.dp)
                }
            }
            
            Text(text = "Payment Methods:", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold), modifier = Modifier.padding(start = 20.dp))


            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround) {
            options.forEach { item ->
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {

                    RadioButton(
                        selected = item == selectedOption.value,
                        onClick = { selectedOption.value = item

                            if (item == options[1]) isSelected.value = true else if(item == options[0]) isSelected.value = false},

                        colors = RadioButtonDefaults.colors(Color.Black)
                    )
                    Surface(
                        modifier = Modifier
                            .height(50.dp)
                            .width(100.dp),
//                            .clip(RoundedCornerShape(12.dp)),
                        elevation = 2.dp,
                        shape = RoundedCornerShape(12.dp),
//                        border = BorderStroke(1.dp, color = Color.Black)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White), verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = item,
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Normal
                                ),
//                                color = Color.White
                            )
                        }
                    }
                }
                }
            }
//            RadioButton(selected = selectedOption.value == options[1], onClick = { selectedOption.value == "Card" },colors = RadioButtonDefaults.colors(Color.Black))
//            RadioButton(selected = selectedOption.value == options[2], onClick = { selectedOption.value == "COD" },colors = RadioButtonDefaults.colors(Color.Black))
            CardPayment(name = cardHolder, card = creditCard,exp = expiry, cvv = cvv, isSelected = isSelected.value, options = options)

            PillButton(title = "Confirm Order", color = Color(0XFF000000).toArgb(), modifier = Modifier.padding(top = 20.dp)){
                //TODO credit card dummy details
                if (creditCard.value == "4532804020291443" && expiry.value == "5/2027" && cvv.value == "633"){
                    Toast.makeText(context,"Payment Success", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context,"Payment Error", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}

@Composable
fun RowComp(title: String,price:String,space: Dp) {
    Row(modifier = Modifier.padding(5.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {

        Text(text = title, style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold))
        
        Spacer(modifier = Modifier.width(space))

        Text(text = price, style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold))
    }
}

@Composable
fun CardPayment(name: MutableState<String>,
                card: MutableState<String>,
                exp: MutableState<String>,
                cvv: MutableState<String>,
                isSelected: Boolean,
                options: List<String>){

    AnimatedVisibility(visible = isSelected) {

        Column(modifier = Modifier.padding(top = 10.dp),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

            TextBox(labelId = "Name", title = name.value, onChange = name)
            TextBox(labelId = "Credit Card Number",title = card.value, onChange = card, keyBoardType = KeyboardType.Number)

            Row {

                TextBox(labelId = "Expiry",title = exp.value, onChange = exp,keyBoardType = KeyboardType.Number, modifier = Modifier
                    .width(180.dp)
                    .height(80.dp))
                TextBox(labelId = "CVV",title = cvv.value, onChange = cvv,keyBoardType = KeyboardType.Number,modifier = Modifier
                    .width(180.dp)
                    .height(80.dp))
            }
        }

    }
}

@Composable
fun PaymentBottomBar(){
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        Text(text = "Secured By ShopKart", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Light))
    }
}
