package com.shoppy.shopkart.models

data class MUser (
    var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var phone_no: String? = null,
    var address: String? = null,
    var profile_image: String? = null){

    fun convertToMap(): MutableMap<String, Any>{

        return mutableMapOf(

            "user_id" to this.id!!,
            "name" to this.name!!,
            "email" to this.email!!,
            "password" to this.password!!,
            "phone_no" to this.phone_no!!,
            "address" to this.address!!,
            "profile_image" to this.profile_image!!

        )

    }
}