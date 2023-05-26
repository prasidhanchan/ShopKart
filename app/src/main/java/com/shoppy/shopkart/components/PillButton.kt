package com.shoppy.shopkart.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoppy.shopkart.ui.theme.roboto

@Composable
fun PillButton(modifier: Modifier = Modifier,
               title: String,
               color: Int,
               textSize: Int = 18,
               shape: Dp = 10.dp,textColor: Color = Color.White,
               enabled: Boolean = true,
               onClick: () -> Unit = {}){

    Button(onClick = {onClick.invoke()},
        modifier = modifier
        .width(340.dp)
        .height(55.dp),
        enabled = enabled,
        shape = RoundedCornerShape(shape),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(color))
    ) {

        Text(text = title,
        style = TextStyle(fontSize = textSize.sp,fontWeight = FontWeight.Bold, fontFamily = roboto,
        color = textColor)
        )

    }

}