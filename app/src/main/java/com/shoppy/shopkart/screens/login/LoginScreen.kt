package com.shoppy.shopkart.screens.login

import android.app.Activity.RESULT_OK
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.identity.Identity
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.PasswordTextBox
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.components.TextBox
import com.shoppy.shopkart.data.SuccessOrError
import com.shoppy.shopkart.navigation.BottomNavScreens
import com.shoppy.shopkart.navigation.NavScreens
import com.shoppy.shopkart.ui.theme.roboto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController,viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val emailState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }
    val errorBlank = remember { mutableStateOf("") }
    val context = LocalContext.current

    //Google Sign In
    val state = viewModel.state.collectAsState()

    val scope = rememberCoroutineScope()

    val googleAuthUiClient = GoogleAuthUiClient(context = context, oneTapClient = Identity.getSignInClient(context))

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == RESULT_OK){
                scope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(intent = result.data?: return@launch)
                    viewModel.onSignInResult(resultData = signInResult)
                }
            }

        })



    Surface(modifier = Modifier.fillMaxSize(), color = ShopKartUtils.offWhite) {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Image(painter = painterResource(id = R.drawable.loginscreen),
                contentDescription = "login",
            modifier = Modifier
                .padding(top = 35.dp)
                .size(300.dp))

            Text(text = "Greatest Deals On Electronics.",
            style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, fontFamily = roboto),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 15.dp)
            )

            Quotes(title = "Biggest discounts are on your way!")
            Quotes(title = "Stay Home Shop Online.")

            TextBox(
                value = emailState.value, labelId = "Email",
                onChange = emailState,
                keyBoardType = KeyboardType.Email,
                leadingIcon = R.drawable.profile)

//            TextBox(
//                value = passwordState.value, labelId = "Password",
//                onChange = passwordState,
//                keyBoardType = KeyboardType.Password,
//                leadingIcon = R.drawable.lock,
//            visualTrans = PasswordVisualTransformation())

            PasswordTextBox(value = passwordState.value, onChange = passwordState, imeAction = ImeAction.Done)

            Text(text = "Forgot Password? ",
                modifier = Modifier.fillMaxWidth()
                    .padding(end = 20.dp)
                    .clickable {
                        navController.navigate(NavScreens.ForgotPasswordScreen.name)
                },
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp, fontFamily = roboto),
                color = Color.Black.copy(alpha = 0.4f),
                textAlign = TextAlign.End)

            Spacer(modifier = Modifier.height(5.dp))

            Text(text = errorBlank.value,
            modifier = Modifier.padding(start = 5.dp, end = 5.dp),
            textAlign = TextAlign.Center, style = TextStyle(fontWeight = FontWeight.Normal, fontFamily = roboto)
            )

            Spacer(modifier = Modifier.height(10.dp))

            PillButton(title = "Login", color = 0xFF3D77E3.toInt(), onClick = {
                if(emailState.value.trim().isNotEmpty() && passwordState.value.trim().isNotEmpty()){

                    viewModel.loginUser(emailState.value, passwordState.value,
                        except = {
                            errorBlank.value = it
                        },
                    nav = {
                        navController.popBackStack()
                        navController.navigate(NavScreens.MainScreenHolder.name)})
                }else{
                    errorBlank.value = "Email and Password cannot be blank"
                }
            })

            //Google Login Icon
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){

                Surface(modifier = Modifier.size(50.dp), shape = RoundedCornerShape(12.dp), border = BorderStroke(2.dp,Color.Black.copy(alpha = 0.2f))) {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                        .clickable {
                            scope.launch {
                                val signInIntentSender = googleAuthUiClient.signIn()
                                launcher.launch(
                                    IntentSenderRequest
                                        .Builder(signInIntentSender ?: return@launch)
                                        .build()
                                )
//                                Log.d("SIGNGOOGLE", "LoginScreen: $signInIntentSender")
                            }
                        }) {
                        Image(
                            painter = painterResource(id = R.drawable.google),
                            contentDescription = "Google Login",
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            Row {

                Text(text = "New to ShopKart? ",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp, fontFamily = roboto),
                color = Color.Black.copy(alpha = 0.4f))
                Text(text = "Sign In",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp, fontFamily = roboto),
                modifier = Modifier.clickable {
                    navController.navigate(NavScreens.RegisterScreen.name) },
                color = Color.Blue.copy(alpha = 0.4f))

            }
        }
    }

    LaunchedEffect(key1 = state.value.error){
        state.value.error?.let { error ->
//            Log.d("GOOGLEE", "LoginScreen: ${state.isSuccess}")
            Toast.makeText(context, error,Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(key1 = state.value.isSuccess) {
        if (state.value.isSuccess) {
//            Toast.makeText(context, "Success",Toast.LENGTH_SHORT).show()
            viewModel.addUserToDB()
            navController.popBackStack()
            navController.navigate(NavScreens.MainScreenHolder.name)
        }
    }
}

@Composable
fun Quotes(title: String){
    Text(text = title,
        style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = roboto),
        color = Color.Black.copy(alpha = 0.4f),
        textAlign = TextAlign.Center,
    )
}