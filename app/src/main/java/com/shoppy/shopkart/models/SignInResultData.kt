package com.shoppy.shopkart.models

data class SignInResultData(
    val data: UserData? = null,
    val errorMessage: String? = null
)

data class UserData(
    val userId: String?,
    val userName: String?,
    val profileUrl: String?
)
