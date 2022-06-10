package rs.appsterdam.appsterdam

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import rs.appsterdam.appsterdam.ui.theme.AppsterdamTheme

class HomeView {
    init {
        println("HomeView")
    }

    @Composable
    fun layout() {
        Column() {
            Text("HomeView Contents")
            Text("HomeView Contents2")
        }
        println("HomeView layout")
    }

    fun getHomeContents() {

    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    AppsterdamTheme {
        HomeView().layout()
    }
}