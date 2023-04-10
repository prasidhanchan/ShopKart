package com.shoppy.shopkart.screens.Login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.shoppy.shopkart.R
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.components.TextBox
import com.shoppy.shopkart.navigation.NavScreens

@Composable
fun LoginScreen(navController: NavController) {

    val emailState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }


    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier=Modifier.fillMaxSize()
            ,verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Image(painter = painterResource(id = R.drawable.loginscreen),
                contentDescription = "login",
            modifier = Modifier
                .padding(top = 15.dp)
                .size(300.dp))

            Text(text = "Greatest Deals On Electronics.",
            style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.ExtraBold),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 15.dp)
            )

            Quotes(title = "Biggest discounts is on your way!")
            Quotes(title = "Stay Home Shop Online.")

            TextBox(title = emailState.value, labelId = "Enter Your Email",
                onChange = emailState,
                leadingIcon = Icons.Rounded.Person,
            modifier = Modifier.padding(top = 15.dp))

            TextBox(title = passwordState.value, labelId = "Enter Your Password",
                onChange = passwordState,
                leadingIcon = Icons.Rounded.Lock)

            Spacer(modifier = Modifier.height(20.dp))

            PillButton(title = "Login")

            Spacer(modifier = Modifier.height(28.dp))

            Row {

                Text(text = "New to ShopKart? ",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                color = Color.Black.copy(alpha = 0.4f))
                Text(text = "Sign In",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                modifier = Modifier.clickable {
                   // TODO Move to Sign In Screen
                                              navController.navigate(NavScreens.RegisterScreen.name)
                },
                color = Color.Blue.copy(alpha = 0.4f))

            }
        }
    }
}

@Composable
fun Quotes(title: String){
    Text(text = title,
        style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold),
        color = Color.Black.copy(alpha = 0.4f),
        textAlign = TextAlign.Center,
    )
}