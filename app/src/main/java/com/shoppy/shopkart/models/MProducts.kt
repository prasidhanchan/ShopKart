package com.shoppy.shopkart.models

data class MProducts(
    var product_url: Any? = null,
    var product_title: String? = null,
    var product_price: Int? = null,
    var product_description: String? = null,
    var stock: Int? = null,
    var category: String? = null,
    var product_id: String? = null
) {

    fun convertToMap(): MutableMap<String,Any>{

        return mutableMapOf(
            "product_url" to this.product_url!!,

            "product_title" to this.product_title!!,

            "product_price" to this.product_price!!,

            "product_description" to this.product_description!!,

            "stock" to this.stock!!,

            "category" to this.category!!,

            "product_id" to this.product_id!!,
        )
    }
}