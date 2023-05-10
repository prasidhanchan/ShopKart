package com.shoppy.shopkart.screens.checkout.payment

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.BackButton
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.components.ProgressBox
import com.shoppy.shopkart.components.TextBox
import com.shoppy.shopkart.components.TextBox2
import com.shoppy.shopkart.models.MCart
import com.shoppy.shopkart.navigation.NavScreens
import com.shoppy.shopkart.screens.checkout.ordersummary.OrderSummaryScreenViewModel
import com.shoppy.shopkart.ui.theme.roboto
import java.text.DecimalFormat


//4532804020291443 5/2027 633
@Composable
fun PaymentScreen(totalAmount: Int,navController: NavHostController,viewModel: OrderSummaryScreenViewModel = hiltViewModel()) {

    val options = listOf("Cash On Delivery", "Credit/Debit Card")

    var itemList = emptyList<MCart>()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    //Filtering cart items to store in "Orders" collection (not displayed on ui)
    itemList = viewModel.fireSummary.value.data!!.toList().filter { mCart ->
        userId == mCart.user_id
    }

    //Payment Method (Cash Or Card)
    val selectedOption = remember { mutableStateOf(options[0]) }


    val deliveryStatus = remember { mutableStateOf("Ordered") }

    val cardHolder = remember { mutableStateOf("") }
    val creditCard = remember { mutableStateOf("") }
    val expiry = remember { mutableStateOf("") }
    val cvv = remember { mutableStateOf("") }

    viewModel.getName{ cardHolder.value = it }

    Scaffold(topBar = { BackButton(navController = navController, topBarTitle = "Payment") },
        backgroundColor = ShopKartUtils.offWhite, bottomBar = { PaymentBottomBar(totalAmount = totalAmount,
            creditCard = creditCard.value,
            expiry = expiry.value, cvv = cvv.value, selectedOption = selectedOption.value, deliveryStatus = deliveryStatus.value, itemsList = itemList, viewModel = viewModel, navController = navController) }) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {

            //Progress Indicator 1-2-3
            Surface(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .height(80.dp),
                elevation = 2.dp,
                color = Color.White) {

                Row(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {

                    ProgressBox(number = "1", title = "Address", color = ShopKartUtils.blue)
                    Divider(modifier = Modifier
                        .height(2.dp)
                        .width(50.dp))
                    ProgressBox(number = "2", title = "Order Summary",color = ShopKartUtils.blue)
                    Divider(modifier = Modifier
                        .height(2.dp)
                        .width(50.dp))
                    ProgressBox(number = "3", title = "Payment",color = ShopKartUtils.blue)
                }
            }
            
            Text(text = "Payment Methods:", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = roboto), modifier = Modifier.fillMaxWidth().padding(start = 20.dp).align(Alignment.Start))

            //Radio Button Card
            Column(modifier = Modifier
                .fillMaxWidth(),
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            options.forEach { item ->
                Surface(
                    modifier = Modifier
                        .height(70.dp)
                        .fillMaxWidth()
                        .padding(start = 18.dp, end = 18.dp, top = 8.dp)
                        .clickable { selectedOption.value = item },
//                    elevation = 2.dp,
                    shape = RoundedCornerShape(12.dp)
                ) {
                Row(modifier = Modifier
                    .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start) {

                    RadioButton(
                        selected = item == selectedOption.value,
                        onClick = { selectedOption.value = item },

                        colors = RadioButtonDefaults.colors(Color.Black), modifier = Modifier
                            .fillMaxHeight()
//                            .fillMaxWidth()
                            .background(Color.White)
                    )

                            Text(
                                text = item,
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = roboto
                                ), modifier = Modifier.fillMaxWidth()
//                                color = Color.White
                            )
                    }
                }
                }
            }
//            RadioButton(selected = selectedOption.value == options[1], onClick = { selectedOption.value == "Card" },colors = RadioButtonDefaults.colors(Color.Black))
//            RadioButton(selected = selectedOption.value == options[2], onClick = { selectedOption.value == "COD" },colors = RadioButtonDefaults.colors(Color.Black))
            CardPayment(name = cardHolder, card = creditCard,exp = expiry, cvv = cvv)
        }

    }

}

@Composable
fun CardPayment(name: MutableState<String>,
                card: MutableState<String>,
                exp: MutableState<String>,
                cvv: MutableState<String>){

//    AnimatedVisibility(visible = isSelected) {

        Column(modifier = Modifier.padding(start = 15.dp, top = 20.dp),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {

            Text(modifier = Modifier.padding(start = 10.dp), text = "Name", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = roboto), color = Color.Black.copy(0.4f))
            TextBox2(value = name.value, onChange = name, placeHolder = "ShopKart")
            Text(modifier = Modifier.padding(start = 10.dp, top = 15.dp),text = "Card Number", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = roboto),color = Color.Black.copy(0.4f))
            TextBox2(value = card.value, onChange = card, trailingIcon =  R.drawable.lock,placeHolder = "1234 5678 1234 5678")

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),horizontalArrangement = Arrangement.Start) {

                Text(modifier = Modifier.padding(start = 10.dp),text = "Expiry Date", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = roboto),color = Color.Black.copy(0.4f))
                Spacer(modifier = Modifier.width(90.dp))
                Text(modifier = Modifier.padding(start = 10.dp),text = "CVV/CVC", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = roboto),color = Color.Black.copy(0.4f))
            }
            Row {


                TextBox2(value = exp.value, onChange = exp,modifier = Modifier
                    .width(180.dp)
                    .height(75.dp),placeHolder = "MM/YYY", trailingIcon = R.drawable.credit_card)


                TextBox2(value = cvv.value, onChange = cvv,modifier = Modifier
                    .width(180.dp)
                    .height(75.dp), placeHolder = "123", trailingIcon = R.drawable.pin)
            }
        }

//    }
}

@Composable
fun PaymentBottomBar(totalAmount: Int,creditCard: String,expiry: String,cvv: String,
                     selectedOption: String,deliveryStatus: String,itemsList: List<MCart>,viewModel: OrderSummaryScreenViewModel,navController: NavHostController){

    val context = LocalContext.current

    Column(verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)
        .background(Color.White)) {

        Text(text = "Total Amount: ₹${DecimalFormat("#,##,###").format(totalAmount.toString().toDouble())}", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = roboto))

        PillButton(title = "Confirm Order", color = ShopKartUtils.black.toInt(), modifier = Modifier.padding(top = 5.dp)){
            //TODO credit card dummy details
            if (selectedOption == "Credit/Debit Card") {
                if(creditCard == "4532804020291443" && expiry == "5/2027" && cvv == "633"){

                    //Uploading items and payment method to Orders collection
                    viewModel.uploadToOrdersAndDeleteCart(itemsList = itemsList, paymentMethod = selectedOption, deliveryStatus = deliveryStatus)
                    navController.navigate(NavScreens.OrderSuccessScreen.name)
            }else{
                Toast.makeText(context,"Payment Error", Toast.LENGTH_SHORT).show()
            }}else if (selectedOption == "Cash On Delivery"){

                //Uploading items and payment method to Orders collection
                viewModel.uploadToOrdersAndDeleteCart(itemsList = itemsList, paymentMethod = selectedOption, deliveryStatus = deliveryStatus)
                navController.navigate(NavScreens.OrderSuccessScreen.name)
            }
        }
        Text(text = "Secured By ShopKart", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal, fontFamily = roboto))
    }
}
