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
import com.shoppy.shopkart.R

@Composable
fun SplashScreen(navController: NavController) {

    val scale = remember{
        androidx.compose.animation.core.Animatable(0f)
    }

    LaunchedEffect(key1 = true,){
        scale.animateTo(targetValue = 0.9f,
            animationSpec = tween(durationMillis = 800))
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
            style= TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
            )


        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun Splash(){
//    val navController = rememberNavController()
//    SplashScreen()
//}