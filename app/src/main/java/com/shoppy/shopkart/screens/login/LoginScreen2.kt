package com.shoppy.shopkart.screens.login

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.shoppy.shopkart.R
import com.shoppy.shopkart.components.LoadingCompAuth
import com.shoppy.shopkart.components.PasswordTextBox
import com.shoppy.shopkart.components.PillButton
import com.shoppy.shopkart.components.TextBox
import com.shoppy.shopkart.navigation.NavScreens
import com.shoppy.shopkart.ui.theme.roboto
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen2(navController: NavController,viewModel: LoginViewModel =  androidx.lifecycle.viewmodel.compose.viewModel()){

    val emailState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }
    val errorBlank = remember { mutableStateOf("") }

    val isLoading = remember { mutableStateOf(false) }

    val context = LocalContext.current

    //Google Sign In
    val state = viewModel.state.collectAsState()

    val scope = rememberCoroutineScope()

    val googleAuthUiClient = GoogleAuthUiClient(context = context, oneTapClient = Identity.getSignInClient(context))

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK){
                scope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(intent = result.data?: return@launch)
                    viewModel.onSignInResult(resultData = signInResult)
                }
            }

        })


    val constraints = ConstraintSet{
        val image = createRefFor(id = "LoginImage")
        val boldQuote = createRefFor(id = "boldQuote")
        val firstQuote = createRefFor(id = "firstQuote")
        val secondQuote = createRefFor(id = "secondQuote")
        val emailTextBox = createRefFor(id = "emailTextBox")
        val passwordTextBox = createRefFor(id = "passwordTextBox")
        val forgotPassword = createRefFor(id = "forgotPassword")
        val errorText = createRefFor(id = "errorText")
        val loginButton = createRefFor(id = "loginButton")
        val loadingComp = createRefFor(id = "loadingComp")
        val googleButton = createRefFor(id = "googleButton")
        val newToShopKart = createRefFor(id = "newToShopKart")
        val signInButton = createRefFor(id = "signInButton")

        constrain(image){
            top.linkTo(parent.top, margin = 45.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(boldQuote.top)
            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }

        constrain(boldQuote){
            top.linkTo(image.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(firstQuote.top)
            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }

        constrain(firstQuote){
            top.linkTo(boldQuote.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(secondQuote.top)
            width = Dimension.wrapContent
            height = Dimension.value(18.dp)
        }

        constrain(secondQuote){
            top.linkTo(firstQuote.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(emailTextBox.top)
            width = Dimension.wrapContent
            height = Dimension.value(18.dp)
        }

        constrain(emailTextBox){
            top.linkTo(secondQuote.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(passwordTextBox.top)
            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }

        constrain(passwordTextBox){
            top.linkTo(emailTextBox.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(forgotPassword.top)
            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }

        constrain(forgotPassword){
            top.linkTo(passwordTextBox.bottom)
            start.linkTo(parent.start, margin = 220.dp)
            end.linkTo(parent.end)
            bottom.linkTo(loginButton.top)
            width = Dimension.wrapContent
            height = Dimension.value(20.dp)
        }

        constrain(errorText){
            top.linkTo(forgotPassword.bottom, margin = 10.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(loginButton.top)
            width = Dimension.wrapContent
            height = Dimension.value(35.dp)
        }

        constrain(loginButton){
            top.linkTo(forgotPassword.bottom, margin = 30.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(googleButton.top)
            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }

        constrain(loadingComp){
            top.linkTo(forgotPassword.bottom, margin = 10.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(googleButton.top)
            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }

        constrain(googleButton){
            top.linkTo(errorText.bottom, margin = 80.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(newToShopKart.top)
            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }

        constrain(newToShopKart){
            top.linkTo(googleButton.bottom)
            start.linkTo(parent.start, margin = 80.dp)
            end.linkTo(signInButton.start)
            bottom.linkTo(parent.bottom)
            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }

        constrain(signInButton){
            top.linkTo(googleButton.bottom)
            start.linkTo(newToShopKart.end)
            end.linkTo(parent.end, margin = 90.dp)
            bottom.linkTo(parent.bottom)
            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }
    }

    ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {

        Image(painter = painterResource(id = R.drawable.loginscreen), contentDescription = "Login Image",
            modifier = Modifier.layoutId("LoginImage").size(300.dp))

        Text(
            text = "Greatest Deals On Electronics.",
            style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold, fontFamily = roboto),
            modifier = Modifier.layoutId("boldQuote")
        )

        Quotes(title = "Biggest discounts are on your way!", modifier = Modifier.layoutId("firstQuote"))
        Quotes(title = "Stay Home Shop Online.", modifier = Modifier.layoutId("secondQuote"))

        TextBox(
            value = emailState.value,
            labelId = "Email",
            onChange = emailState,
            keyBoardType = KeyboardType.Email,
            leadingIcon = R.drawable.profile,
            modifier = Modifier.layoutId("emailTextBox"))

        PasswordTextBox(value = passwordState.value, onChange = passwordState, imeAction = ImeAction.Done, modifier = Modifier.layoutId("passwordTextBox"))

        Text(text = "Forgot Password? ",
            modifier = Modifier
                .layoutId("forgotPassword")
                .padding(end = 20.dp)
                .clickable { navController.navigate(NavScreens.ForgotPasswordScreen.name) },
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp, fontFamily = roboto),
            color = Color.Black.copy(alpha = 0.4f),
            textAlign = TextAlign.End)

        Text(text = errorBlank.value,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp).layoutId("errorText"),
            textAlign = TextAlign.Center, style = TextStyle(fontWeight = FontWeight.Normal, fontFamily = roboto), maxLines = 3, overflow = TextOverflow.Ellipsis
        )

        //Show Loading composable if isLoading false else shew PillButton
        if (isLoading.value) LoadingCompAuth(modifier = Modifier.layoutId("loadingComp"))

        else PillButton(title = "Login", color = 0xFF3D77E3.toInt(),
            modifier = Modifier.layoutId("loginButton").padding(top = 10.dp, bottom = 10.dp), onClick = {
        if(emailState.value.trim().isNotEmpty() && passwordState.value.trim().isNotEmpty()){

            isLoading.value = true

            viewModel.loginUser(emailState.value, passwordState.value,
                except = {
                    isLoading.value = false
                    errorBlank.value = it
                },
                nav = {
                    isLoading.value = false
                    navController.popBackStack()
                    navController.navigate(NavScreens.MainScreenHolder.name)})
        }else{
            errorBlank.value = "Email and Password cannot be blank"
        }
    })


        Surface(modifier = Modifier.size(50.dp).layoutId("googleButton"),
            shape = RoundedCornerShape(12.dp), border = BorderStroke(2.dp,Color.Black.copy(alpha = 0.2f))) {
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
                                .build())
                    }
                }) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Google Login",
                    contentScale = ContentScale.Crop
                )
            }
        }

        Text(text = "New to ShopKart? ",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp, fontFamily = roboto),
            color = Color.Black.copy(alpha = 0.4f), modifier = Modifier.layoutId("newToShopKart").padding(top = 10.dp, bottom = 10.dp))
        
        Text(text = "Sign In",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp, fontFamily = roboto),
            modifier = Modifier.layoutId("signInButton").padding(top = 10.dp, bottom = 10.dp).clickable {
                navController.navigate(NavScreens.RegisterScreen.name) },
            color = Color.Blue.copy(alpha = 0.4f))
        
    }


    LaunchedEffect(key1 = state.value.error){
        state.value.error?.let { error ->
            isLoading.value = false
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
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

@Composable
fun Quotes(title: String,modifier: Modifier = Modifier){
    Text(text = title,
        style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = roboto),
        color = Color.Black.copy(alpha = 0.4f),
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun Prev(){
    LoginScreen2(navController = rememberNavController())
}