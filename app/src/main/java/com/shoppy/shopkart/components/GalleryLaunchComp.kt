package com.shoppy.shopkart.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
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
fun GalleryLaunchComp(modifier: Modifier = Modifier,
                      title: String,
                      color: Color,
                      onClick: () ->Unit = {}){

    Surface(modifier = modifier
        .fillMaxWidth()
        .height(150.dp)
        .padding(top = 25.dp, start = 20.dp, end = 20.dp, bottom = 10.dp),
        color = color,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Icon(painter = painterResource(id = R.drawable.ic_gallery),
                contentDescription = "Select Sliders",
                modifier = Modifier.clickable { onClick() })

            Text(text = title,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = roboto)
            )
        }
    }
}