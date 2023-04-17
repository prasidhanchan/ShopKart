package com.shoppy.shopkart.models

import android.net.Uri

class MSliders(
    private val sliderUrl: Uri?
) {

    fun convertToMap(): MutableMap<String, Any?>{

        return mutableMapOf(
            "slider_image" to this.sliderUrl
        )

    }
}