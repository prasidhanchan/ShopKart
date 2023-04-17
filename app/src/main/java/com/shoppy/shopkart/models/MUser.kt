package com.shoppy.shopkart.models

class MUser (
    val id: String?,
    val name: String,
    val email: String,
    val password: String,
    val address: String){

    fun convertToMap(): MutableMap<String, Any>{

        return mutableMapOf(

            "user_id" to this.id!!,
            "name" to this.name,
            "email" to this.email,
            "password" to this.password,
            "address" to this.address

        )

    }
}