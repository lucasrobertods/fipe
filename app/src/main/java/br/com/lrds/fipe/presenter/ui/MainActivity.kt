package br.com.lrds.fipe.presenter.ui

import Favorite
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.lrds.fipe.presenter.ui.home.Home
import br.com.lrds.fipe.shared.ui.theme.FipeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FipeTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "home") {
                    composable(
                        route = "home"
                    ) {
                        Home(navController)
                    }
                    composable(
                        route = "favorite"
                    ) {
                        Favorite(navController)
                    }
                }
            }
        }
    }
}