package com.shoppy.shopkart.screens.login

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.shoppy.shopkart.R
import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.components.LoadingComp
import com.shoppy.shopkart.components.LoadingCompAuth
import com.shoppy.shopkart.components.PasswordTextBox
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.components.TextBox
import com.shoppy.shopkart.navigation.NavScreens
import com.shoppy.shopkart.ui.theme.roboto
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun LoginScreen(navController: NavController,viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val emailState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }
    val errorBlank = remember { mutableStateOf("") }

    val isLoading = remember { mutableStateOf(false) }

    val context = LocalContext.current

    val height = LocalConfiguration.current.screenHeightDp
    val width = LocalConfiguration.current.screenWidthDp

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

        BoxWithConstraints(
            modifier = Modifier.width(width.dp).height(height.dp),
            contentAlignment = Alignment.TopCenter
        ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.loginscreen),
                    contentDescription = "login",
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .size(300.dp)
                )

                Text(
                    text = "Greatest Deals On Electronics.",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = roboto
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 15.dp)
                )

                Quotes(title = "Biggest discounts are on your way!")
                Quotes(title = "Stay Home Shop Online.")

                TextBox(
                    value = emailState.value,
                    labelId = "Email",
                    onChange = emailState,
                    keyBoardType = KeyboardType.Email,
                    leadingIcon = R.drawable.profile
                )

                PasswordTextBox(
                    value = passwordState.value,
                    onChange = passwordState,
                    imeAction = ImeAction.Done
                )

                Text(
                    text = "Forgot Password? ",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp)
                        .clickable { navController.navigate(NavScreens.ForgotPasswordScreen.name) },
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        fontFamily = roboto
                    ),
                    color = Color.Black.copy(alpha = 0.4f),
                    textAlign = TextAlign.End
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = errorBlank.value,
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontWeight = FontWeight.Normal, fontFamily = roboto)
                )

                Spacer(modifier = Modifier.height(10.dp))

                //Show Loading composable if isLoading false else shew PillButton
                if (isLoading.value) LoadingCompAuth()
                else PillButton(title = "Login", color = 0xFF3D77E3.toInt(), onClick = {
                    if (emailState.value.trim().isNotEmpty() && passwordState.value.trim()
                            .isNotEmpty()
                    ) {

                        isLoading.value = true

                        viewModel.loginUser(emailState.value, passwordState.value,
                            except = {
                                isLoading.value = false
                                errorBlank.value = it
                            },
                            nav = {
                                isLoading.value = false
                                navController.popBackStack()
                                navController.navigate(NavScreens.MainScreenHolder.name)
                            })
                    } else {
                        errorBlank.value = "Email and Password cannot be blank"
                    }
                })

                //Google Login Icon
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Surface(
                        modifier = Modifier.size(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(2.dp, Color.Black.copy(alpha = 0.2f))
                    ) {
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                            .clickable {
                                scope.launch {
                                    isLoading.value = true
                                    val signInIntentSender = googleAuthUiClient.signIn()
                                    launcher.launch(
                                        IntentSenderRequest
                                            .Builder(signInIntentSender ?: return@launch)
                                            .build()
                                    )
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

                    Text(
                        text = "New to ShopKart? ",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            fontFamily = roboto
                        ),
                        color = Color.Black.copy(alpha = 0.4f)
                    )
                    Text(
                        text = "Sign In",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            fontFamily = roboto
                        ),
                        modifier = Modifier.clickable {
                            navController.navigate(NavScreens.RegisterScreen.name)
                        },
                        color = Color.Blue.copy(alpha = 0.4f)
                    )

                }
            }
        }
    }

    LaunchedEffect(key1 = state.value.error){
        state.value.error?.let { error ->
            isLoading.value = false
            Toast.makeText(context, error,Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(key1 = state.value.isSuccess) {
        if (state.value.isSuccess) {
            viewModel.addUserToDB()
            delay(2000)
            isLoading.value = false
            navController.popBackStack()
            navController.navigate(NavScreens.MainScreenHolder.name)
        }
    }
}