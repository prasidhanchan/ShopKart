package com.shoppy.shopkart.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.shoppy.shopkart.MainActivity
import com.shoppy.shopkart.R
import kotlin.random.Random

class FirebaseCloudMessage: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (tiramisuCheck()){

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val activityIntent = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this,0,activityIntent, PendingIntent.FLAG_IMMUTABLE)

            createNotification(notificationManager)

            activityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val notification = NotificationCompat.Builder(this,"Channel_Id")
            .setContentTitle(remoteMessage.data["title"])
            .setContentText(remoteMessage.data["message"])
            .setSmallIcon(R.drawable.logo)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            //parsing String to Uri
//            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(uriToBitmap(res = Uri.parse(notificationImg.toString()), context = context)))
            .build()

        notificationManager.notify(Random.nextInt(),notification)

        }else{
            Toast.makeText(this,"Enable Notifications",Toast.LENGTH_SHORT).show()
        }
    }

     private fun createNotification(notificationManager: NotificationManager) {
        super.onCreate()
        val channelDeliveryStatus = NotificationChannel("Channel_Id","Delivery Status",NotificationManager.IMPORTANCE_HIGH)
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channelDeliveryStatus)
    }

    private fun tiramisuCheck(): Boolean{
        return if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU){
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        }else{
            true
        }
    }
}