package com.shoppy.shopkart.screens.myprofile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shoppy.shopkart.components.BackButton

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MyProfileScreen(navController: NavController){
    Scaffold(topBar = { BackButton(navController = navController) }, modifier = Modifier
        .fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(text="My Profile Screen",
                modifier = Modifier.padding(top = 2.dp),
                style= TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
            )
        }
    }
}