package com.shoppy.shopkart.screens.checkout.payment

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.RadioButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.BackButton
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.components.ProgressBox
import com.shoppy.shopkart.components.TextBox2
import com.shoppy.shopkart.models.MCart
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.screens.checkout.ordersummary.OrderSummaryScreenViewModel
import com.shoppy.shopkart.ui.theme.roboto
import java.text.DecimalFormat


//4532804020291443 5/2027 633
@Composable
fun PaymentScreen(
    totalAmount: Int,
    navController: NavHostController,
    viewModel: OrderSummaryScreenViewModel = hiltViewModel()
) {

    val options = listOf("Cash On Delivery", "Credit/Debit Card")

    var itemList = emptyList<MCart>()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    //Filtering cart items to store in "Orders" collection (not displayed on ui)
    itemList = viewModel.fireSummary.value.data!!.toList().filter { mCart ->
        userId == mCart.user_id
    }

    //Payment Method (Cash Or Card)
    val selectedOption = remember { mutableStateOf(options[0]) }

    val isSelected = remember { mutableStateOf(false) }

    when (selectedOption.value) {
        "Credit/Debit Card" -> isSelected.value = true
        else -> isSelected.value = false
    }

    //defaulting to Ordered
    val deliveryStatus = remember { mutableStateOf("Ordered") }

    val cardHolder = remember { mutableStateOf("") }
    val creditCard = remember { mutableStateOf("") }
    val expiry = remember { mutableStateOf("") }
    val cvv = remember { mutableStateOf("") }

    viewModel.getName { cardHolder.value = it }


    val constraints = ConstraintSet {
        val progressCard = createRefFor("ProgressCard")
        val paymentMethodTitle = createRefFor("PaymentMethodTitle")
        val radioButton = createRefFor("RadioButton")
        val cardPayment = createRefFor("CardPayment")

        constrain(progressCard) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(paymentMethodTitle.top)
            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }

        constrain(paymentMethodTitle) {
            top.linkTo(progressCard.bottom, margin = 20.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(radioButton.top)
            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }

        constrain(radioButton) {
            top.linkTo(paymentMethodTitle.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(cardPayment.top)
            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }

        constrain(cardPayment) {
            top.linkTo(radioButton.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            width = Dimension.wrapContent
            height = Dimension.fillToConstraints
        }
    }


    Scaffold(topBar = { BackButton(navController = navController, topBarTitle = "Payment") },
        backgroundColor = ShopKartUtils.offWhite, bottomBar = {
            PaymentBottomBar(
                totalAmount = totalAmount,
                creditCard = creditCard.value,
                expiry = expiry.value,
                cvv = cvv.value,
                selectedOption = selectedOption.value,
                deliveryStatus = deliveryStatus.value,
                itemsList = itemList,
                viewModel = viewModel,
                navController = navController
            )
        }) { innerPadding ->


        ConstraintLayout(
            constraintSet = constraints, modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(), animateChanges = true
        ) {


            //Progress Indicator 1-2-3
            Surface(
                modifier = Modifier
                    .layoutId("ProgressCard")
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
                            .width(40.dp)
                    )
                    ProgressBox(number = "2", title = "Order Summary", color = ShopKartUtils.blue)
                    Divider(
                        modifier = Modifier
                            .height(2.dp)
                            .width(40.dp)
                    )
                    ProgressBox(number = "3", title = "Payment", color = ShopKartUtils.blue)
                }
            }

            Text(
                text = "Payment Methods:",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = roboto
                ),
                modifier = Modifier
                    .layoutId("PaymentMethodTitle")
                    .fillMaxWidth()
                    .padding(start = 20.dp)
            )


            //Radio Button Card
            Column(
                modifier = Modifier
                    .layoutId("RadioButton")
                    .padding(start = 10.dp, end = 10.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                options.forEach { item ->
                    Surface(
                        modifier = Modifier
                            .height(60.dp)
                            .fillMaxWidth()
                            .padding(start = 18.dp, end = 18.dp, top = 8.dp)
                            .clickable { selectedOption.value = item },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .background(Color.White),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {

                            RadioButton(
                                selected = item == selectedOption.value,
                                onClick = { selectedOption.value = item },

                                colors = RadioButtonDefaults.colors(Color.Black),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .background(Color.White)
                            )

                            Text(
                                text = item,
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = roboto
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = isSelected.value,
                modifier = Modifier.layoutId("CardPayment"),
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                CardPayment(
                    name = cardHolder,
                    card = creditCard,
                    exp = expiry,
                    cvv = cvv,
                    modifier = Modifier
                        .verticalScroll(state = rememberScrollState()),
                )
            }
        }
    }

}

@Composable
fun CardPayment(
    name: MutableState<String>,
    card: MutableState<String>,
    exp: MutableState<String>,
    cvv: MutableState<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(start = 15.dp, end = 10.dp, top = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = "Name",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = roboto),
            color = Color.Black.copy(0.4f)
        )
        TextBox2(value = name.value, onChange = name, placeHolder = "ShopKart")
        Text(
            modifier = Modifier.padding(start = 10.dp, top = 10.dp),
            text = "Card Number",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = roboto),
            color = Color.Black.copy(0.4f)
        )
        TextBox2(
            value = card.value,
            onChange = card,
            trailingIcon = R.drawable.lock,
            placeHolder = "1234 5678 1234 5678"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp), horizontalArrangement = Arrangement.Start
        ) {

            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = "Expiry Date",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = roboto
                ),
                color = Color.Black.copy(0.4f)
            )
            Spacer(modifier = Modifier.width(90.dp))
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = "CVV/CVC",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = roboto
                ),
                color = Color.Black.copy(0.4f)
            )
        }
        Row {

            TextBox2(
                value = exp.value, onChange = exp, modifier = Modifier
                    .width(180.dp)
                    .height(75.dp), placeHolder = "MM/YYY", trailingIcon = R.drawable.credit_card
            )


            TextBox2(
                value = cvv.value,
                onChange = cvv,
                modifier = Modifier
                    .width(180.dp)
                    .height(75.dp),
                placeHolder = "123",
                trailingIcon = R.drawable.pin,
                imeAction = ImeAction.Done
            )
        }
    }
}

@Composable
fun PaymentBottomBar(
    totalAmount: Int,
    creditCard: String,
    expiry: String,
    cvv: String,
    selectedOption: String,
    deliveryStatus: String,
    itemsList: List<MCart>,
    viewModel: OrderSummaryScreenViewModel,
    navController: NavHostController
) {

    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(Color.White)
    ) {

        Text(
            text = "Total Amount: ₹${
                DecimalFormat("#,##,###").format(
                    totalAmount.toString().toDouble()
                )
            }",
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = roboto)
        )

        PillButton(
            title = "Confirm Order",
            color = ShopKartUtils.black.toInt(),
            modifier = Modifier.padding(top = 5.dp)
        ) {

            if (selectedOption == "Credit/Debit Card") {
                if (creditCard == "4532804020291443" && expiry == "5/2027" && cvv == "633") {

                    //Uploading items and payment method to Orders collection
                    viewModel.uploadToOrdersAndDeleteCart(
                        itemsList = itemsList,
                        paymentMethod = selectedOption,
                        deliveryStatus = deliveryStatus
                    ) {

                        navController.navigate(BottomNavScreens.OrderSuccessScreen.route) {
                            popUpTo(
                                id = navController.graph.findStartDestination().id
                            )
                        }
                    }

                } else {
                    Toast.makeText(context, "Payment Error", Toast.LENGTH_SHORT).show()
                }
            } else if (selectedOption == "Cash On Delivery") {

                //Uploading items and payment method to Orders collection
                viewModel.uploadToOrdersAndDeleteCart(
                    itemsList = itemsList,
                    paymentMethod = selectedOption,
                    deliveryStatus = deliveryStatus
                ) {

                    //Navigating to OrderSuccess Screen and popping all previous screens till Home Screen
                    navController.navigate(BottomNavScreens.OrderSuccessScreen.route) { popUpTo(id = navController.graph.findStartDestination().id) }
                }

            }
        }
        Text(
            text = "Secured By ShopKart",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = roboto,
                color = Color.Black.copy(alpha = 0.5f)
            )
        )
    }
}

@Preview
@Composable
fun Prev() {
    PaymentScreen(totalAmount = 1000, navController = rememberNavController())
}