package rs.appsterdam.app

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import rs.appsterdam.app.models.Appsterdamer
import rs.appsterdam.app.ui.theme.AppsterdamTheme
import java.net.URL

class AboutView {
    private var aboutText = MutableLiveData("Loading...")

    init {
        println("AboutView")
        GlobalScope.launch(Dispatchers.IO) {

            val str = URL("https://appsterdam.rs/api/app.json").readText()
            val json = JSONObject(str)

            val gson = Gson()
            var homeValue = gson.fromJson(json.toString(), Appsterdamer::class.java)

            // How to get the home var in MarkdownText
            // or update MarkdownText
            println("APP:Home=" + homeValue)

            GlobalScope.launch(Dispatchers.Main) {
                aboutText.value = homeValue.home.toString()
                println("APP:UV=" + aboutText.value)
            }
        }
    }

    @Composable
    fun layout() {
        val mdState = aboutText.observeAsState()
        var spacing = 10.dp

        Spacer(Modifier.height(spacing))
        Text("LOGO")
        Spacer(Modifier.height(spacing))
        centeredText("Appsterdam")
        centeredText("Version: 1.0")
        Spacer(Modifier.height(spacing))
        centeredText("SomestaticText")
        Spacer(Modifier.height(spacing))
        Text("Appsterdam Team")
        // Board...
        // - PPL
        // More organizers
        // - PPL

        for (i in 0..10) {
            Column {
                Text("Section $i")
                // Horizontal list of people?
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    for (i2 in 0..5) {
                        Text("Person $i2")
                    }
                }
            }
        }

        Spacer(Modifier.height(spacing))
        centeredText("Discord")
        Divider()
        centeredText("Facebook")
        Divider()
        centeredText("Twitter")
        Divider()
        centeredText("YouTube")
        Spacer(Modifier.height(spacing))
        centeredText("Code Of Conduct")
        Spacer(Modifier.height(spacing))
        centeredText("Privacy Policy")
        Spacer(Modifier.height(spacing))
        centeredText("© 2012-2022 Stichting Appsterdam. All rights reserved.")

        println("AboutView layout")
        println(mdState.value.toString())
    }

    @Composable
    fun centeredText(text: String) {
        Text(text,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AboutViewPreview() {
    AppsterdamTheme {
        AboutView().layout()
    }
}