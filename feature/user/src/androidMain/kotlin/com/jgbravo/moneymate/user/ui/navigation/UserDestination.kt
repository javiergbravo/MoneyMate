package com.jgbravo.moneymate.user.ui.navigation

import com.jgbravo.moneymate.core.ui.navigation.Destination


sealed class UserDestination(final override val route: String, vararg allParams: String) : Destination {
    final override val params: ArrayList<String> = allParams.toCollection(ArrayList())
    override operator fun invoke(): String = route

    data object SignInDestination : UserDestination("signIn")
    data object SignUpDestination : UserDestination("signUp")
    data object ForgotPasswordDestination : UserDestination("forgotPassword")
    data object VerifyEmailDestination : UserDestination("verifyEmail")
    data object ProfileDestination : UserDestination("profile")
}