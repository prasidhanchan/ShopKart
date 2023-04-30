package com.shoppy.shopkart.screens.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shoppy.shopkart.components.BackButton
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.components.TextBox
import com.shoppy.shopkart.navigation.NavScreens
import com.shoppy.shopkart.screens.login.Quotes

@Composable
fun RegisterScreen(navController: NavController,viewModel: RegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){

    val nameState = rememberSaveable { mutableStateOf("") }
    val emailState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }
    val phoneState = rememberSaveable { mutableStateOf("") }
    val addressState = rememberSaveable { mutableStateOf("") }
    val errorState = rememberSaveable { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {


            //Back Button
            BackButton(navController = navController)


            Text(text = "Welcome To ShopKart",
                style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.ExtraBold),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 15.dp)
                    .padding(top = 90.dp))

            Quotes(title = "Biggest discounts are on your way!")
            Quotes(title = "Stay Home Shop Online.")

            TextBox(
                title = nameState.value, labelId = "Name",
                onChange = nameState,
                keyBoardType = KeyboardType.Text,
                leadingIcon = Icons.Rounded.Person,
                modifier = Modifier.padding(top = 15.dp))

            TextBox(
                title = emailState.value, labelId = "Email",
                onChange = emailState,
                keyBoardType = KeyboardType.Email,
                leadingIcon = Icons.Rounded.Email)

            TextBox(
                title = passwordState.value, labelId = "Password",
                onChange = passwordState,
                keyBoardType = KeyboardType.Password,
                leadingIcon = Icons.Rounded.Lock,
                visualTrans = PasswordVisualTransformation())

            TextBox(
                title = phoneState.value, labelId = "Phone no",
                onChange = phoneState,
                keyBoardType = KeyboardType.Number,
                leadingIcon = Icons.Rounded.Phone)

            TextBox(
                title = addressState.value, labelId = "Address",
                onChange = addressState,
                keyBoardType = KeyboardType.Text,
                leadingIcon = Icons.Rounded.List,
                isSingleLine = false)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = errorState.value,
                modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(10.dp))

            PillButton(title = "Register", color = 0xFF8BC34A.toInt(), onClick = {
                if(nameState.value !="" &&
                    emailState.value != "" &&
                    passwordState.value != "" &&
                    phoneState.value != "" &&
                    addressState.value !=""){

                    if (emailState.value.contains("admin.")){
                        errorState.value = "Admin account cannot be created here contact ShopKart"
                    }else{

                    viewModel.createUser(emailState.value,passwordState.value,nav = {

                        //Adding user to Firebase Firestore DB
                        viewModel.addUserToDB(uName = nameState.value,
                            uEmail = emailState.value.trim(),
                            uPassword = passwordState.value.trim(),
                            uPhone = phoneState.value.trim(),
                            uAddress = addressState.value.trim())
                        navController.navigate(NavScreens.LoginScreen.name)
                    },
                        regExcept = {
                            errorState.value = it
                        })
                }}else{
                    errorState.value = "Fields cannot be empty"
                }
            })

            Spacer(modifier = Modifier.height(28.dp))

            Row {

                Text(text = "Already a member? ",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                    color = Color.Black.copy(alpha = 0.4f))
                Text(text = "Login",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                    modifier = Modifier.clickable {
                        navController.navigate(NavScreens.LoginScreen.name)
                    },
                    color = Color.Blue.copy(alpha = 0.4f))

            }

        }
    }
}