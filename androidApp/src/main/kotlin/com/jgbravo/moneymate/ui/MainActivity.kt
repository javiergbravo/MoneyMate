package com.jgbravo.moneymate.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.jgbravo.moneymate.core.ui.theme.MoneyMateTheme
import com.jgbravo.moneymate.user.ui.navigation.UserNavGraph

class MainActivity : ComponentActivity() {

//    private val googleAuthUiClient by lazy {
//        GoogleAuthUiClient(oneTapClient = Identity.getSignInClient(applicationContext))
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyMateTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    UserNavGraph(
                        navController = navController
                    )
                }
            }
            /*MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {

                            val viewModel: ANDLoginViewModel by viewModel()
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.loginManager.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(key1 = state.isUserLoggedIn) {
                                if (state.isUserLoggedIn) {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "User logged in",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            LoginScreen(
                                state = state,
                                onAction = { action ->
                                    when (action) {
                                        is OnGoogleLoginClicked -> {
                                            lifecycleScope.launch {
                                                val signInIntentSender = googleAuthUiClient.signIn()
                                                launcher.launch(
                                                    Builder(
                                                        signInIntentSender ?: return@launch
                                                    ).build()
                                                )
                                            }
                                        }
                                        else -> viewModel.onAction(action)
                                    }
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }*/
        }
    }
}