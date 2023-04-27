package com.shoppy.shopkart.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartColors

@Composable
fun ShopKartAppBar(userNameState: String?){

    val searchState = remember {
        mutableStateOf("")
    }

    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(160.dp),
        color = ShopKartColors.offWhite) {

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {

            Row(modifier = Modifier.padding(10.dp),
                horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
                
                Box(modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))){

                    Image(painter = painterResource(id = R.drawable.logo), contentDescription = "ShopKart Logo" )
                }
                Spacer(modifier = Modifier.width(15.dp))

                Text(text = "ShopKart", style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.ExtraBold))

                Spacer(modifier = Modifier.width(120.dp))

                Text(text = "Hello,\n$userNameState", textAlign = TextAlign.Center, style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold))

            }
            TextBox(title = searchState.value, labelId = "Search", onChange = searchState, leadingIcon = Icons.Rounded.Search)
        }

    }
}

@Preview
@Composable
fun Prev(){
//    ShopKartAppBar("")
}