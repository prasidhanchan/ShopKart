package com.shoppy.shopkart.screens.myorderdetails

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shoppy.shopkart.components.BackButton

@Composable
fun MyOrderDetailsScreen(navController: NavController){
    Scaffold(topBar = { BackButton(navController = navController) }, modifier = Modifier
        .fillMaxSize()) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding).fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(text="My Order Details",
                modifier = Modifier.padding(top = 2.dp),
                style= TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
            )
        }
    }
}