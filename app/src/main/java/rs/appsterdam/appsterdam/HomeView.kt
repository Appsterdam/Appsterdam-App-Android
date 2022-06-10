package rs.appsterdam.appsterdam

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.jeziellago.compose.markdowntext.MarkdownText
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