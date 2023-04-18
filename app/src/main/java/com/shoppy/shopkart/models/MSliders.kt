package com.shoppy.shopkart.models

import android.net.Uri
import kotlin.random.Random
import kotlin.random.nextUInt

class MSliders(
    private val sliderUrl: Uri?
) {

    private val uniqueId = Random.nextUInt()

    fun convertToMap(): MutableMap<String, Any?>{

        return mutableMapOf(
            "slider_image$uniqueId" to this.sliderUrl
        )

    }
}