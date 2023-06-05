package com.shoppy.shopkart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shoppy.shopkart.ShopKartUtils

//Progress Bar
@Composable
fun LoadingComp() {
    Column(modifier = Modifier.fillMaxSize().background(ShopKartUtils.offWhite),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

        Spacer(modifier = Modifier.height(250.dp))

        CircularProgressIndicator(color = Color.Black)
    }
}

@Composable
fun LoadingComp2() {
    Column(modifier = Modifier.fillMaxSize().background(ShopKartUtils.offWhite),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

//        Spacer(modifier = Modifier.height(250.dp))

        CircularProgressIndicator(color = Color.Black)
    }
}

@Composable
fun LoadingCompAuth(modifier: Modifier = Modifier) {
        CircularProgressIndicator(color = Color.Black, backgroundColor = Color.Transparent, modifier = modifier.padding(5.dp))
}