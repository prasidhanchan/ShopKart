package com.shoppy.shopkart.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.core.app.NotificationCompat
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.shoppy.shopkart.MainActivity
import com.shoppy.shopkart.R
import kotlin.random.Random

class Notification(private val context: Context) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    suspend fun showNotification(title: String, text: String, notificationImg: Any){

        val activityIntent = Intent(context,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context,0,activityIntent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context,"Channel_Id")
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.logo)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
                //parsing String to Uri
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(uriToBitmap(res = Uri.parse(notificationImg.toString()), context = context)))
            .build()

        notificationManager.notify(Random.nextInt(),notification)

    }

}

//Converting Uri to Bitmap
private suspend fun uriToBitmap(res: Uri,context: Context): Bitmap{

    val loading = ImageLoader(context)
    val request = ImageRequest.Builder(context).data(res).build()

    val result = (loading.execute(request) as SuccessResult).drawable

    return (result as BitmapDrawable).bitmap
}