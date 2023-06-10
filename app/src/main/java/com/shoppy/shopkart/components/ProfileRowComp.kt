package com.shoppy.shopkart.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ui.theme.roboto

@Composable
fun ProfileRowComp(leadingIcon: Int? = null, title: String,onClick:() -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 8.dp)
        .clickable { onClick.invoke() }, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        leadingIcon?.let { painterResource(id = it) }
            ?.let { Icon(painter = it, contentDescription = title) }
        Text(text = title, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = roboto))
        Icon(painter = painterResource(id = R.drawable.arrow_forward), contentDescription = "Arrow Forward", tint = Color.Black.copy(alpha = 0.5f))
    }
}