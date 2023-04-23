package com.shoppy.shopkart.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shoppy.shopkart.ShopKartColors

@Composable
fun CartScreen(navController: NavController){
    Column(modifier = Modifier
        .background(ShopKartColors.offWhite)
        .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center) {
        Text(text="Cart Screen",
            style= TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
    }
}