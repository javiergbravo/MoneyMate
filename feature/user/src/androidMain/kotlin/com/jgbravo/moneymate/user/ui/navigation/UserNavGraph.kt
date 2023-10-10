package com.jgbravo.moneymate.user.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jgbravo.logger.Logger
import com.jgbravo.moneymate.user.ui.forgotpassword.ForgotPasswordAction
import com.jgbravo.moneymate.user.ui.forgotpassword.ForgotPasswordScreen
import com.jgbravo.moneymate.user.ui.forgotpassword.ForgotPasswordViewModel
import com.jgbravo.moneymate.user.ui.navigation.UserDestination.ForgotPasswordDestination
import com.jgbravo.moneymate.user.ui.navigation.UserDestination.SignInDestination
import com.jgbravo.moneymate.user.ui.navigation.UserDestination.SignUpDestination
import com.jgbravo.moneymate.user.ui.signin.SignInAction.OnForgotPasswordClick
import com.jgbravo.moneymate.user.ui.signin.SignInAction.OnSignUpClick
import com.jgbravo.moneymate.user.ui.signin.SignInScreen
import com.jgbravo.moneymate.user.ui.signin.SignInViewModel
import com.jgbravo.moneymate.user.ui.signup.SignUpAction.OnBackClick
import com.jgbravo.moneymate.user.ui.signup.SignUpAction.OnSignInClick
import com.jgbravo.moneymate.user.ui.signup.SignUpScreen
import com.jgbravo.moneymate.user.ui.signup.SignUpViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = SignInDestination(),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(
            route = SignInDestination()
        ) {
            val viewModel = koinViewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            SignInScreen(
                state = state,
                onAction = { action ->
                    when (action) {
                        OnForgotPasswordClick -> navController.navigate(ForgotPasswordDestination())
                        OnSignUpClick -> navController.navigate(SignUpDestination())
                        else -> viewModel.onAction(action)
                    }
                },
                onLoginSuccess = {
                    Logger.d("Login success -> Navigate to home screen")
                }
            )
        }

        composable(
            route = SignUpDestination()
        ) {
            val viewModel = koinViewModel<SignUpViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            SignUpScreen(
                state = state,
                onAction = { action ->
                    when (action) {
                        OnSignInClick,
                        OnBackClick -> navController.popBackStack()
                        else -> viewModel.onAction(action)
                    }
                },
                onSignUpSuccess = {
                    Logger.d("Sign up success -> Navigate to home screen")
                }
            )
        }
        composable(
            route = ForgotPasswordDestination()
        ) {
            val viewModel = koinViewModel<ForgotPasswordViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            ForgotPasswordScreen(
                state = state,
                onAction = { action ->
                    when (action) {
                        ForgotPasswordAction.OnBackClick -> navController.popBackStack()
                        else -> viewModel.onAction(action)
                    }
                },
                onForgotPasswordSuccess = { navController.popBackStack() }
            )
        }
//        composable(
//            route = VerifyEmailDestination()
//        ) {
//            VerifyEmailScreen(
//                navigateToProfileScreen = {
//                    navController.navigate(ProfileDestination()) {
//                        popUpTo(navController.graph.id) {
//                            inclusive = true
//                        }
//                    }
//                }
//            )
//        }
//        composable(
//            route = ProfileDestination()
//        ) {
//            ProfileScreen()
//        }
    }
}