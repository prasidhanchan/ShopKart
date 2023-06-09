package com.shoppy.shopkart

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.compose.ui.graphics.Color


object ShopKartUtils{

    //Colors
//    val offWhite = Color(0xFFF8F6F6)
    val offWhite = Color(0xFFF5F5F5)
    val blue = Color(0xFF5588FF)
    val darkBlue = Color(0xFF1C0927)
    const val black = 0xFF23272a

    const val webClientId = "75755133388-rc7nfobjf70i2kk7pgunapfke5cf39mt.apps.googleusercontent.com"

    //FCM constants
    const val API_KEY = "AIzaSyC9bCetIXBbWLSHQ90n0unX2xRFxKIydFw"
    const val SERVER_KEY = "AAAAEaNbmcw:APA91bEMNIttOuq2EsbEeCDbqJiyGsu2l2263FjCiuUFEwWofhkIH7naic_bXnTXWWbSA_hcAE5Ymxck54GCrJFCJsZnbBifzRJfbjTfRBENLHwOHTUnPAMyC_FQdpzQkVBYChQlolD9"
    const val BASE_URL = "https://fcm.googleapis.com/"
    const val CONTENT_TYPE = "application/json"
    val TOPIC = if (VERSION.SDK_INT >= VERSION_CODES.S) "Delivery Status" else "/topics/Delivery Status"

//    const val RZP_API_KEY = "rzp_test_YQFWpJM7HeMRZ9"
}
