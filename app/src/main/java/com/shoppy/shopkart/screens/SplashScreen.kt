package com.shoppy.shopkart.screens

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.shoppy.shopkart.R
import com.shoppy.shopkart.navigation.NavScreens
import com.shoppy.shopkart.ui.theme.roboto
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    val scale = remember{
        androidx.compose.animation.core.Animatable(0f)
    }

    LaunchedEffect(key1 = true){
        scale.animateTo(targetValue = 0.9f,
            animationSpec = tween(durationMillis = 800))
        delay(1000L)
        navController.popBackStack()
        if(FirebaseAuth.getInstance().currentUser != null){

            navController.navigate(NavScreens.MainScreenHolder.name)

        }else{

            navController.navigate(NavScreens.LoginScreen.name)
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
            
            Box(modifier = Modifier.size(80.dp))
                {

                Image(painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo",
                modifier = Modifier.scale(scale.value))
            }

            Text(text="Happy Shopping!",
                modifier = Modifier.padding(top = 2.dp),
            style= TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = roboto))
        }
    }
}