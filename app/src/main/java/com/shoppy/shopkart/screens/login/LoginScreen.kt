package com.shoppy.shopkart.screens.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shoppy.shopkart.R
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.components.TextBox
import com.shoppy.shopkart.navigation.NavScreens

@Composable
fun LoginScreen(navController: NavController,viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val emailState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }
    var errorBlank = remember { mutableStateOf("") }
    val context = LocalContext.current


    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier=Modifier.fillMaxSize()
            ,verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Image(painter = painterResource(id = R.drawable.loginscreen),
                contentDescription = "login",
            modifier = Modifier
                .padding(top = 48.dp)
                .size(300.dp))

            Text(text = "Greatest Deals On Electronics.",
            style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.ExtraBold),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 15.dp)
            )

            Quotes(title = "Biggest discounts are on your way!")
            Quotes(title = "Stay Home Shop Online.")

            TextBox(
                value = emailState.value, labelId = "Email",
                onChange = emailState,
                keyBoardType = KeyboardType.Email,
                leadingIcon = Icons.Rounded.Person,
            modifier = Modifier.padding(top = 15.dp))

            TextBox(
                value = passwordState.value, labelId = "Password",
                onChange = passwordState,
                keyBoardType = KeyboardType.Password,
                leadingIcon = Icons.Rounded.Lock,
            visualTrans = PasswordVisualTransformation())

            Spacer(modifier = Modifier.height(5.dp))

            Text(text = errorBlank.value,
            modifier = Modifier.padding(start = 5.dp, end = 5.dp),
            textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(10.dp))

            PillButton(title = "Login", color = 0xFF3D77E3.toInt(), onClick = {
                if(emailState.value.trim().isNotEmpty() && passwordState.value.trim().isNotEmpty()){

                    viewModel.loginUser(emailState.value, passwordState.value, toast = {
                        Toast.makeText(context,"Login Successful",Toast.LENGTH_LONG).show()
                        errorBlank.value = ""
                    },
                        except = {
                            errorBlank.value = it
                        },
                    nav = {navController.popBackStack()
//                        navController.navigate(NavScreens.MainScreenHolder.name + "/${emailState.value}") })
                        navController.navigate(NavScreens.MainScreenHolder.name) })
                }else{
                    errorBlank.value = "Email and Password cannot be blank"
                }
            })

            Spacer(modifier = Modifier.height(28.dp))

            Row {

                Text(text = "New to ShopKart? ",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                color = Color.Black.copy(alpha = 0.4f))
                Text(text = "Sign In",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                modifier = Modifier.clickable {
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