package com.shoppy.shopkart.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.BackButton
import com.shoppy.shopkart.components.ShopKartAppBar
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.ui.theme.roboto

@Composable
fun SearchScreen(navController: NavHostController) {

    val searchState = remember { mutableStateOf("") }

    Scaffold(topBar = { BackButton(navController = navController, topBarTitle = "Search") }, backgroundColor = ShopKartUtils.offWhite) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding).padding(start = 20.dp, end = 20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {

            SearchBox(value = searchState.value, onChange = searchState, leadingIcon = R.drawable.ic_search)


        }
    }
}

@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    value: String,
    onChange: MutableState<String>,
    leadingIcon: Int
) {

    val focusReq = remember { FocusRequester() }

    TextField(
        value = value,
        onValueChange = { onChange.value = it },
        modifier = modifier
            .padding(10.dp)
            .width(340.dp)
            .focusRequester(focusReq), leadingIcon = { Icon(painter = painterResource(id = leadingIcon), contentDescription = value) },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color.White),
        textStyle = TextStyle(fontFamily = roboto),
        shape = RoundedCornerShape(10.dp),
        enabled = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        placeholder = { Text(text = "Search Products", color = Color.Black.copy(alpha = 0.4f), style = TextStyle(fontFamily = roboto)) }
    )

    LaunchedEffect(Unit){
        focusReq.requestFocus()
    }
}