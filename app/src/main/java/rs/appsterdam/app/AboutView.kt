package rs.appsterdam.app

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import rs.appsterdam.app.ui.theme.AppsterdamTheme

class AboutView {
    init {
        println("AboutView")
    }

    @Composable
    fun layout() {
        Text("AboutView Contents")
        println("AboutView layout")
    }
}

@Preview(showBackground = true)
@Composable
fun AboutViewPreview() {
    AppsterdamTheme {
        AboutView().layout()
    }
}