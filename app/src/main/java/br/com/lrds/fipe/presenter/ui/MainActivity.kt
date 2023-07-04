package br.com.lrds.fipe.presenter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.lrds.fipe.domain.model.Brand
import br.com.lrds.fipe.presenter.ui.viewmodel.FipeViewModel
import br.com.lrds.fipe.shared.ui.ViewState
import br.com.lrds.fipe.shared.ui.theme.FipeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val fipeViewModel: FipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FipeTheme {
                val state = fipeViewModel.viewState.observeAsState().value
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home(state = state)
                }
            }
        }
        fipeViewModel.loadBrands()
    }
}

@Composable
fun Home(state: ViewState?) {
    when(state) {
        is ViewState.Loading -> LoadingState()
        is ViewState.Content<*> -> BrandList(state.data as List<Brand>)
        is ViewState.Error -> Text("Error NULL")
        null -> Text("Error NULL")
    }
}

@Composable
fun LoadingState() {
    Text("Loading...")
}

@Composable
fun BrandList(list : List<Brand>) {
    Column {
        list.forEach {
            Text(text = it.name)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    FipeTheme {
        Home(state = ViewState.Loading)
    }
}