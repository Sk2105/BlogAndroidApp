package com.composeapp.blogapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.composeapp.blogapp.presentation.navigation.AppNavigation
import com.composeapp.blogapp.presentation.navigation.graph.AppGraph
import com.composeapp.blogapp.presentation.viewmodel.MainViewModel
import com.composeapp.blogapp.ui.theme.BlogAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color(0xff212121).toArgb()),
            navigationBarStyle = SystemBarStyle.dark(Color(0xff212121).toArgb())
        )

        installSplashScreen()

        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            val token = viewModel.getUserToken().collectAsState(initial = "").value
            viewModel.userToken.value = token
            var route: AppGraph = AppGraph.Auth

            if (token.isNotEmpty()) {
                route = AppGraph.HomeScreen
            }

            BlogAppTheme {
                AppNavigation(route = route, viewModel = viewModel)
            }
        }
    }
}