package com.shoppy.shopkart.network

import com.shoppy.shopkart.ShopKartUtils
import com.shoppy.shopkart.models.PushNotificationData
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import javax.inject.Singleton

interface NotificationApi {

//    @Singleton
    @Headers("Authorization: key = ${ShopKartUtils.SERVER_KEY}", "Content-Type: ${ShopKartUtils.CONTENT_TYPE}")
    @POST("fcm/send")
    suspend fun postNotification( @Body notification: PushNotificationData): Response<ResponseBody>
}