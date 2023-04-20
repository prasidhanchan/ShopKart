package com.shoppy.shopkart.models

import android.net.Uri

data class MProducts(
    var product_url: Any? = null,
    var product_title: String? = null,
    var product_price: String? = null,
    var product_description: String? = null
) {

    fun convertToMap(): MutableMap<String,Any>{

        return mutableMapOf(
            "product_url" to this.product_url!!,

            "product_title" to this.product_title!!,

            "product_price" to this.product_price!!,

            "product_description" to this.product_description!!
        )
    }
}