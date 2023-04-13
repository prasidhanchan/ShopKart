package com.shoppy.shopkart.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

//val leagueSpartan = FontFamily(
//        Font(R.font.leaguespartan_bold, weight = FontWeight.Bold),
//        Font(R.font.leaguespartan_extra_bold, weight = FontWeight.ExtraBold),
//        Font(R.font.leaguespartan_regular, weight = FontWeight.Normal),
//        Font(R.font.leaguespartan_light, weight = FontWeight.Light)
//)

// Set of Material typography styles to start with
val Typography = Typography(
        body1 = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
        )
        /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)