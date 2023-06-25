package com.shoppy.shopkart.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.BackButton
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.components.TextBox
import com.shoppy.shopkart.screens.login.LoginViewModel
import com.shoppy.shopkart.ui.theme.roboto

@Composable
fun ForgotPasswordScreen(navHostController: NavHostController,viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){

    val emailState = remember { mutableStateOf("") }
    val newPasswordState = remember { mutableStateOf("") }

    val successState = remember { mutableStateOf("") }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = { BackButton(navController = navHostController, topBarTitle = "Forgot Password", spacing = 50.dp) }, backgroundColor = ShopKartUtils.offWhite) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding).padding(20.dp).fillMaxSize(), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {

            TextBox(value = emailState.value, labelId = "Email", onChange = emailState, leadingIcon = R.drawable.email, keyBoardType = KeyboardType.Email, imeAction = ImeAction.Next)
            TextBox(value = newPasswordState.value, labelId = "New Password", onChange = newPasswordState, leadingIcon = R.drawable.lock, keyBoardType = KeyboardType.Password, imeAction = ImeAction.Done)

            PillButton(title = "Find Account", color = ShopKartUtils.black.toInt(), modifier = Modifier.padding(top = 10.dp)){
                if (emailState.value.isEmpty()){
                    successState.value = "Enter Your Email"
                } else if (newPasswordState.value.isEmpty()){
                    successState.value = "Enter Your New Password"
                } else{
                    viewModel.forgotPassword(email = emailState.value, newPassword = newPasswordState.value, success = { successState.value = "We have sent a password reset link to your email" }){ successState.value = it } }
            }

            Text(text = successState.value, style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = roboto), modifier = Modifier.padding(top = 10.dp), textAlign = TextAlign.Center)

        }

    }
}