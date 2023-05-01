package com.shoppy.shopkart.screens.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shoppy.shopkart.ShopKartUtils

@Composable
fun OrdersScreen(navController: NavController){
    Column(modifier = Modifier
        .background(ShopKartUtils.offWhite)
        .fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(text="Orders Screen",
            modifier = Modifier.padding(top = 2.dp),
            style= TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
        )
    }
}