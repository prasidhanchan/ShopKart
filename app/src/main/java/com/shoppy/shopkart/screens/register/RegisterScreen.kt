package com.shoppy.shopkart.screens.register

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.BackButton
import com.shoppy.shopkart.components.PasswordTextBox
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.components.TextBox
import com.shoppy.shopkart.screens.login.Quotes
import com.shoppy.shopkart.ui.theme.roboto

@Composable
fun RegisterScreen(navController: NavController,viewModel: RegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){

    val nameState = rememberSaveable { mutableStateOf("") }
    val emailState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }
    val phoneState = rememberSaveable { mutableStateOf("") }
    val addressState = rememberSaveable { mutableStateOf("") }
    val errorState = rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    val height = LocalConfiguration.current.screenHeightDp
    val width = LocalConfiguration.current.screenWidthDp

    Surface(modifier = Modifier.fillMaxSize(), color = ShopKartUtils.offWhite) {

        BoxWithConstraints(modifier = Modifier.width(width.dp).height(height.dp), contentAlignment = Alignment.TopCenter) {

            Column(verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {


                //Back Button
                BackButton(navController = navController)


                Text(text = "Welcome To ShopKart",
                    style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.ExtraBold, fontFamily = roboto),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                        .padding(top = 90.dp))

                Quotes(title = "Biggest discounts are on your way!")
                Quotes(title = "Stay Home Shop Online.")

                TextBox(
                    value = nameState.value, labelId = "Name",
                    onChange = nameState,
                    keyBoardType = KeyboardType.Text,
                    leadingIcon = R.drawable.profile)

                TextBox(
                    value = emailState.value, labelId = "Email",
                    onChange = emailState,
                    keyBoardType = KeyboardType.Email,
                    leadingIcon = R.drawable.email)

                PasswordTextBox(value = passwordState.value, onChange = passwordState)

                TextBox(
                    value = phoneState.value, labelId = "Phone no",
                    onChange = phoneState,
                    keyBoardType = KeyboardType.Phone,
                    leadingIcon = R.drawable.call)

                TextBox(
                    value = addressState.value, labelId = "Address",
                    onChange = addressState,
                    keyBoardType = KeyboardType.Text,
                    leadingIcon = R.drawable.address,
                    isSingleLine = false,
                    imeAction = ImeAction.Done)

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = errorState.value,
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                    textAlign = TextAlign.Center, style = TextStyle(fontFamily = roboto)
                )

                Spacer(modifier = Modifier.height(10.dp))

                PillButton(title = "Register", color = 0xFF8BC34A.toInt(), onClick = {
                    if(nameState.value !="" &&
                        emailState.value != "" &&
                        passwordState.value != "" &&
                        phoneState.value != "" &&
                        addressState.value !=""){

                        if (emailState.value.contains("admin.")){
                            errorState.value = "Email cannot have admin"
                        }else if (emailState.value.contains("employee.")) {
                            errorState.value = "Employee account cannot be created here contact ShopKart"
                        }else if (phoneState.value.length > 10) {
                            errorState.value = "Enter a valid Phone number"
                        }else{

                            viewModel.createUser(emailState.value,passwordState.value,nav = {

                                //Adding user to Firebase Firestore DB
                                viewModel.addUserToDB(uName = nameState.value,
                                    uEmail = emailState.value.trim(),
                                    uPassword = passwordState.value.trim(),
                                    uPhone = phoneState.value.trim(),
                                    uAddress = addressState.value.trim())
                                navController.popBackStack()
//                        navController.navigate(NavScreens.LoginScreen.name)
                                Toast.makeText(context,"Account Created", Toast.LENGTH_SHORT).show()
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
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp, fontFamily = roboto),
                        color = Color.Black.copy(alpha = 0.4f))
                    Text(text = "Login",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp, fontFamily = roboto),
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        },
                        color = Color.Blue.copy(alpha = 0.4f))

                }

            }

        }
    }
}