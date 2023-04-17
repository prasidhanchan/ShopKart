package com.shoppy.shopkart.models

import android.net.Uri

class MProducts(
    private val productUrl: Uri?,
    private val productTitle: String,
    private val productPrice: String,
    private val productDesc: String
) {

    fun convertToMap(): MutableMap<String,Any>{

        return mutableMapOf(
            "product_url" to this.productUrl!!,

            "product_title" to this.productTitle,

            "product_price" to this.productPrice,

            "product_description" to this.productDesc
        )
    }
}