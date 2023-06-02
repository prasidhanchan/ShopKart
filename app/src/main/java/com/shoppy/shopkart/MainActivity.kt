package com.shoppy.shopkart

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.app.NotificationCompat
import com.shoppy.shopkart.navigation.ShopKartNavigation
import com.shoppy.shopkart.ui.theme.ShopKartTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopKartTheme {
                ShopKartApp()
            }
        }

        fun showNotification(){
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notification = NotificationCompat.Builder(applicationContext,"Channel_Id")
                .setContentTitle("On the Way")
                .setContentText("Your order is on the way")
                .setSmallIcon(R.drawable.logo)
                .setAutoCancel(false)
                .build()
            notificationManager.notify(1,notification)
        }

//        showNotification()

    }
}

@Composable
fun ShopKartApp(){
    ShopKartNavigation()
}