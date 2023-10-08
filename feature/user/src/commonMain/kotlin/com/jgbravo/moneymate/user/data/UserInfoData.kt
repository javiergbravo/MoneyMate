package com.jgbravo.moneymate.user.data

data class UserInfoData(
    val userId: String? = null,
    val displayName: String? = null,
    val email: String? = null,
    val photoUrl: String? = null,
    val phoneNumber: String? = null,
    val providerId: String? = null,
    val isEmailVerified: Boolean = false
)