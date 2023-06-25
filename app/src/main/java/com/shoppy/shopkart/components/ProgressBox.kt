package com.shoppy.shopkart.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoppy.shopkart.ui.theme.roboto

@Composable
fun ProgressBox(number: String,title: String,color: Color) {
    Column(modifier = Modifier.padding(start = 10.dp, end = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.size(20.dp)){
            Surface(modifier = Modifier.fillMaxSize(),
                shape = CircleShape, color = color) {
                Text(text = number, textAlign = TextAlign.Center, style = TextStyle(fontSize = 12.sp, fontFamily = roboto), color = Color.White, modifier = Modifier.padding(top = 1.dp))
            }
        }
        Text(text = title,style = TextStyle(fontSize = 14.sp, fontFamily = roboto))
    }
}