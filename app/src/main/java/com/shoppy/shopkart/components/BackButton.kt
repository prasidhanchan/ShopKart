package com.shoppy.shopkart.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shoppy.shopkart.ui.theme.roboto


@Composable
fun BackButton(navController: NavController,topBarTitle: String = "",spacing: Dp = 80.dp) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, top = 30.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.Start
    ) {

        Surface(
            modifier = Modifier
                .size(50.dp),
            shape = CircleShape,
//            border = BorderStroke(width = 2.dp, color = Color.Black.copy(0.1f))
        ) {

            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { navController.popBackStack() },
                contentAlignment = Alignment.Center
            ) {

                Image(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back",
                    modifier = Modifier)
            }
        }
        
        Spacer(modifier = Modifier.width(spacing))

        Text(text = topBarTitle, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, fontFamily = roboto), modifier = Modifier.padding(top = 10.dp))
    }
}