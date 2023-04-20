package com.shoppy.shopkart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.shoppy.shopkart.navigation.ShopKartNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopKartApp()
        }
    }
}

@Composable
fun ShopKartApp(){
            ShopKartNavigation()
}