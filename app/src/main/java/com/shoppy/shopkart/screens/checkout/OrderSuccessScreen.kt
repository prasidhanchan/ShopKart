package com.shoppy.shopkart.screens.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.shoppy.shopkart.R
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.navigation.NavScreens
import com.shoppy.shopkart.ui.theme.roboto

@Composable
fun OrderSuccessScreen(navController: NavHostController) {
Column(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
    
    Image(painter = painterResource(id = R.drawable.order_success), contentDescription = "Order Success", modifier = Modifier
        .size(300.dp)
        .clip(RoundedCornerShape(12.dp)))
    
    Text(text = "Order Success", style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, fontFamily = roboto),
        modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 20.dp, bottom = 20.dp))

    Text(text = "Your packet will be sent to your \n address, thanks for order", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium, fontFamily = roboto), textAlign = TextAlign.Center, color = Color.Black.copy(alpha = 0.4f))
    
    PillButton(title = "Back To Home", color = Color(0XFF000000).toArgb(),modifier = Modifier.padding(top = 25.dp)){ navController.navigate(NavScreens.MainScreenHolder.name)
//        navController.navigate(BottomNavScreens.Cart.route)
    }
}
}