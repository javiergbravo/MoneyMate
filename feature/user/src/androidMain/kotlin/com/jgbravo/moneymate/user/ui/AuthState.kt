package com.jgbravo.moneymate.user.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jgbravo.moneymate.user.ui.navigation.UserNavGraph

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
//    private val viewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    navController = rememberNavController()

                    UserNavGraph(navController = navController)
                }
            }
        }
    }
}