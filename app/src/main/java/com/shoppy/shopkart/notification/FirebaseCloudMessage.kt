package com.shoppy.shopkart.notification

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.shoppy.shopkart.R

class FirebaseCloudMessage: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FBMESSAGE", "Refreshed token: ${super.onNewToken(token)}")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
//        sendRegistrationToServer(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.d("FBMESSAGE", "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("FBMESSAGE", "Message data payload: ${remoteMessage.data}")

            if (remoteMessage.notification != null) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                super.onMessageReceived(remoteMessage)
        }

            val channelId = "ShopKart"
            val notificationChannel =  NotificationChannel(channelId,"",NotificationManager.IMPORTANCE_HIGH)

            getSystemService(NotificationManager::class.java).createNotificationChannel(notificationChannel)

            val notification = Notification.Builder(this,channelId)
                .setContentTitle("On The Way")
                .setContentText("Order is on the way and may arrive soon")
                .setSmallIcon(R.drawable.logo)
                .setAutoCancel(false)

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }
            NotificationManagerCompat.from(this).notify(1,notification.build())


            // Check if message contains a notification payload.
            remoteMessage.notification?.let {
                Log.d("FBMESSAGE", "Message Notification Body: ${it.body}")
            }

            // Also if you intend on generating your own notifications as a result of a received FCM
            // message, here is where that should be initiated. See sendNotification method below.
        }

    }
}