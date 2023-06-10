package com.shoppy.shopkart.components

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
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shoppy.shopkart.R
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.ui.theme.roboto


@Composable
fun BackButton(navController: NavController,topBarTitle: String = "",spacing: Dp = 80.dp,modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 30.dp, top = 30.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.Start
    ) {

        Surface(
            modifier = Modifier
                .size(50.dp),
            shape = CircleShape,
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

        if (topBarTitle == "Details"){
            Spacer(modifier = Modifier.width(100.dp))

            Icon(painter = painterResource(id = R.drawable.ic_cart), contentDescription = "Go to Cart", tint = Color.Black,
                modifier = Modifier.size(30.dp).clickable { navController.navigate(BottomNavScreens.Cart.route) })
        }else{
            Box{}
        }
    }
}