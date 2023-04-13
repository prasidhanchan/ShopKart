package com.shoppy.shopkart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PillButton(modifier: Modifier = Modifier,
               title: String,
               color: Int,
               onClick: () -> Unit = {}){

    Button(onClick = {onClick.invoke()},
        modifier = modifier
        .width(340.dp)
        .height(55.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(color))
    ) {

        Text(text = title,
        style = TextStyle(fontSize = 18.sp,fontWeight = FontWeight.Medium,
        color = Color.White)
        )

    }

}