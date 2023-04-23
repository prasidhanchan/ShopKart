package com.shoppy.shopkart.models

import kotlin.random.Random
import kotlin.random.nextUInt

data class MSliders(
    var slider_image: Any? = null
) {

    fun convertToMap(): MutableMap<String, Any?>{

        return mutableMapOf(
            "slider_image" to this.slider_image
        )

    }
}