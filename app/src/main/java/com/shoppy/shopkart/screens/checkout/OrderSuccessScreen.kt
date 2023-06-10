package com.shoppy.shopkart.screens.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.ui.theme.roboto

@Composable
fun OrderSuccessScreen(navController: NavHostController) {

    val haptic = LocalHapticFeedback.current

Column(modifier = Modifier
    .fillMaxSize()
    .background(ShopKartUtils.offWhite),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

    Surface(modifier = Modifier.size(300.dp), shape = RoundedCornerShape(12.dp)) {

        Image(painter = painterResource(id = R.drawable.order_success), contentDescription = "Order Success")
    }

    Text(text = "Order Success", style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, fontFamily = roboto),
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 20.dp, bottom = 20.dp))

    Text(text = "Your packet will be sent to your \n address, thanks for order", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium, fontFamily = roboto), textAlign = TextAlign.Center, color = Color.Black.copy(alpha = 0.4f))

    //Haptic Feedback
    LaunchedEffect(Unit){
        haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
    }

    PillButton(title = "Back To Home", color = ShopKartUtils.black.toInt(),modifier = Modifier.padding(top = 25.dp)){
//        navController.popBackStack()
        navController.navigate(BottomNavScreens.Home.route){ popUpTo(id = navController.graph.findStartDestination().id) }
//        navController.navigate(BottomNavScreens.Cart.route)
    }
}
}

@Preview(showBackground = true)
@Composable
fun Prev(){
    OrderSuccessScreen(navController = rememberNavController())
}