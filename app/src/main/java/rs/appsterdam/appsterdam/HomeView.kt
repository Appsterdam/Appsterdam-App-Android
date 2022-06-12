package rs.appsterdam.appsterdam

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.jeziellago.compose.markdowntext.MarkdownText
import rs.appsterdam.appsterdam.ui.theme.AppsterdamTheme
import java.net.URL
import java.io.File
import org.json.JSONObject
import kotlinx.coroutines.*

class HomeView {
    init {
        println("HomeView")
        GlobalScope.launch(Dispatchers.IO) {

            val str = URL("https://appsterdam.rs/api/app.json").readText()
          val json = JSONObject(str)

          val home = json.getString("home")
            // How to get the home var in MarkdownText
            // or update MarkdownText
          println("Home=" + home)
        }
    }

    @Composable
    fun layout() {
        Column() {
            Text("HomeView Contents")
            Text("HomeView Contents2")

            MarkdownText("""
                # Test markdown
                This is a test
                and this should work

                # Sample
                * Markdown
                * [Link](https://example.com)
                <a href="https://www.google.com/">Google</a>
                """.trimIndent()
            )
        }
        println("HomeView layout")
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    AppsterdamTheme {
        HomeView().layout()
    }
}