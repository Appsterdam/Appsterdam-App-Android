package rs.appsterdam.app

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import dev.jeziellago.compose.markdowntext.MarkdownText
import rs.appsterdam.app.ui.theme.AppsterdamTheme
import java.net.URL
import org.json.JSONObject
import kotlinx.coroutines.*
import rs.appsterdam.app.models.Appsterdamer
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class HomeView {
    private var markdown = MutableLiveData("Loading...")

    init {
        println("APP: HomeView")
        GlobalScope.launch(Dispatchers.IO) {

            val str = URL("https://appsterdam.rs/api/app.json").readText()
            val json = JSONObject(str)

            val gson = Gson()
            val homeValue = gson.fromJson(json.toString(), Appsterdamer::class.java)

            // How to get the home var in MarkdownText
            // or update MarkdownText
            println("APP:Home=$homeValue")

            GlobalScope.launch(Dispatchers.Main) {
                markdown.value = homeValue.home.toString()
                println("APP: UV=" + markdown.value)
            }
        }
    }

    @Composable
    fun Layout() {
        // Make the markdown
        val mdState = markdown.observeAsState()

        Column {
            val spacing = 10.dp

            Spacer(androidx.compose.ui.Modifier.height(spacing + spacing))

            Image(
                painter = painterResource(R.drawable.appsterdam_logo),
                contentDescription = "Appsterdam Logo",
                modifier = androidx.compose.ui.Modifier
                    .height(
                        Resources
                            .getSystem()
                            .displayMetrics
                            .widthPixels.dp / 5
                    )
                    .fillMaxWidth()
            )

            Spacer(androidx.compose.ui.Modifier.height(spacing))

            MarkdownText(
                mdState.value.toString()
                        // Markdown editor ignores one line break, so we need 2
                    .replace("\n", "\n\n"),
                fontSize = 18.sp,
            )
        }
        println("APP: HomeView layout")
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    AppsterdamTheme {
        HomeView().Layout()
    }
}