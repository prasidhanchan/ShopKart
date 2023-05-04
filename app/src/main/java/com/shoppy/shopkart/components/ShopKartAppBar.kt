package com.shoppy.shopkart.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.ui.theme.roboto

@Composable
fun ShopKartAppBar(userName: String?, profile_url: String?){

    val searchState = remember {
        mutableStateOf("")
    }

    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(160.dp),
        color = ShopKartUtils.offWhite) {

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

                Text(text = "ShopKart", style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.ExtraBold, fontFamily = roboto))

                Spacer(modifier = Modifier.width(50.dp))

                Text(text = "Hello,\n$userName",
                    textAlign = TextAlign.Center, maxLines = 2,
                    overflow = TextOverflow.Ellipsis, modifier = Modifier
                        .width(60.dp)
                        .padding(end = 5.dp), style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = roboto))

                Surface(modifier = Modifier.size(45.dp), shape = CircleShape, border = BorderStroke(2.dp,Color.Black)) {

                    if (profile_url != "") AsyncImage(model = profile_url, contentDescription = "Profile Image", placeholder = painterResource(id = R.drawable.dummy_profile), contentScale = ContentScale.Crop)
                    else Image(painter = painterResource(id = R.drawable.dummy_profile), contentDescription = "Profile Image")

                }
            }
            SearchBox(value = searchState.value, onChange = searchState, leadingIcon = Icons.Rounded.Search, placeHolder = "MacBook Pro")
        }

    }
}

@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    value: String,
    onChange: MutableState<String>,
    keyBoardType: KeyboardType = KeyboardType.Text,
    leadingIcon: ImageVector? = null,
    placeHolder: String = ""
) {
    TextField(
        value = value,
        onValueChange = { onChange.value = it },
        modifier = modifier
            .padding(10.dp)
            .width(340.dp), leadingIcon = {
            if (leadingIcon != null) {
                Icon(imageVector = leadingIcon, contentDescription = value)
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color(0xFFE0ECEA)
        ),
        textStyle = TextStyle(fontFamily = roboto),
        keyboardOptions = KeyboardOptions(keyboardType = keyBoardType),
        shape = RoundedCornerShape(10.dp),
        enabled = true,
        placeholder = { Text(text = placeHolder, color = Color.Black.copy(alpha = 0.4f), style = TextStyle(fontFamily = roboto))})
}

@Preview
@Composable
fun Prev(){
//    ShopKartAppBar("")
}