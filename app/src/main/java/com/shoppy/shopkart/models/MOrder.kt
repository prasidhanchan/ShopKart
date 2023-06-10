package com.shoppy.shopkart.models

data class MOrder(
    var timestamp: Any? = null,
    var item_count: Int? = null,
    var user_id: String? = null,
    var product_url: Any? = null,
    var product_title: String? = null,
    var product_description: String? = null,
    var product_price: Int? = null,
    var delivery_status: String? = null,
    var order_date: String? = null,
    var payment_method: String? = null,
    var order_id: String? = null,
    //This field is for the notification to be displayed only once
    var notificationCount: Int? = null
)
