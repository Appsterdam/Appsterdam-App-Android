package rs.appsterdam.app

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import dev.jeziellago.compose.markdowntext.MarkdownText
import rs.appsterdam.app.ui.theme.AppsterdamTheme
import java.net.URL
import org.json.JSONObject
import kotlinx.coroutines.*
import rs.appsterdam.app.models.Appsterdamer
import kotlinx.coroutines.flow.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.sp
import java.lang.reflect.Modifier

class HomeView {
    private var markdown = MutableLiveData("Loading...")

    init {
        println("APP:HomeView")
        GlobalScope.launch(Dispatchers.IO) {

            val str = URL("https://appsterdam.rs/api/app.json").readText()
            val json = JSONObject(str)

            val gson = Gson()
            var homeValue = gson.fromJson(json.toString(), Appsterdamer::class.java)

            // How to get the home var in MarkdownText
            // or update MarkdownText
            println("APP:Home=" + homeValue)

            GlobalScope.launch(Dispatchers.Main) {
                markdown.value = homeValue.home.toString()
                println("APP:UV=" + markdown.value)
            }
        }
    }

    @Composable
    fun layout() {
        val mdState = markdown.observeAsState()

        // modifier: Modifier = Modifier
        Column {
            Text("APPSTERDAM LOGO")

            MarkdownText(
                mdState.value.toString(),
                fontSize = 21.sp,
            )
        }
        println("APP:HomeView layout")
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    AppsterdamTheme {
        HomeView().layout()
    }
}