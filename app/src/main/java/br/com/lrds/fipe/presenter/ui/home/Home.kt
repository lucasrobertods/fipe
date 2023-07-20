package br.com.lrds.fipe.presenter.ui.home

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.lrds.fipe.presenter.ui.home.components.FavoriteAction
import br.com.lrds.fipe.shared.ui.components.Toolbar
import br.com.lrds.fipe.shared.ui.theme.FipeTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(navController: NavHostController) {
    Scaffold(
        topBar = {
            Toolbar(
                title = "Fipe",
                actions = {
                    FavoriteAction(
                        onClick = {
                            navController.navigate("favorite")
                        }
                    )
                }
            )
        },
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    FipeTheme {
        Home(rememberNavController())
    }
}