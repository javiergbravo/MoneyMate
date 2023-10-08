package com.jgbravo.moneymate.user.signin

data class SignInResult(
    val data: UserData? = null,
    val errorMessage: String? = null
)

data class UserData(
    val userId: String,
    val userName: String?,
    val profilePictureUrl: String?
)
