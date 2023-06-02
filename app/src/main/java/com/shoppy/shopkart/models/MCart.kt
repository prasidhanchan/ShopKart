package com.shoppy.shopkart.models

data class MCart(
    var timestamp: Any? = null,
    var item_count: Int? = null,
    var user_id: String? = null,
    var product_url: Any? = null,
    var product_title: String? = null,
    var product_description: String? = null,
    var product_price: Int? = null,
    var stock: Int? = null,
    var category: String? = null,
    var product_id: String? = null
){
    fun convertToMap(): MutableMap<String,Any?>{

        return mutableMapOf(
        "timestamp" to this.timestamp!!,
        "item_count" to this.item_count!!,
        "user_id" to this.user_id!!,
        "product_url" to this.product_url!!,
        "product_title" to this.product_title!!,
        "product_description" to this.product_description!!,
        "product_price" to this.product_price!!,
        "stock" to this.stock!!,
        "category" to this.category!!,
        "product_id" to this.product_id!!
        )
    }
}
