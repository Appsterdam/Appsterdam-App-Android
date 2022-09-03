package rs.appsterdam.app.ui.screens

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import rs.appsterdam.app.BuildConfig
import rs.appsterdam.app.R
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
            val homeValue = gson.fromJson(json.toString(), Appsterdamer::class.java)

            // How to get the home var in MarkdownText
            // or update MarkdownText
            println("APP:Home=$homeValue")

            GlobalScope.launch(Dispatchers.Main) {
                aboutText.value = homeValue.home.toString()
                println("APP:UV=$aboutText.value")
            }
        }
    }

    @Composable
    fun Layout() {
        Column {
            val uriHandler = LocalUriHandler.current
            val mdState = aboutText.observeAsState()
            val spacing = 10.dp

            Spacer(Modifier.height(spacing + spacing))

            Image(
                painter = painterResource(R.drawable.appsterdam_logo),
                contentDescription = "Appsterdam Logo",
                modifier = Modifier
                    .height(
                        Resources
                            .getSystem()
                            .getDisplayMetrics()
                            .widthPixels.dp / 5
                    )
                    .fillMaxWidth()
            )

            Spacer(Modifier.height(spacing))
            CenteredText("Appsterdam")
            CenteredText("Version: " + BuildConfig.VERSION_NAME)
            Spacer(Modifier.height(spacing + spacing))
            CenteredText(
                "“If you want to make movies, go to Hollywood.\n" +
                        "If you want to make musicals, go to Broadway.\n" +
                        "If you want to make apps, go to Appsterdam.”"
            )
            Text(
                "- Mike Lee ",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right
            )
            Spacer(Modifier.height(spacing * 2))
            Text("Appsterdam Team")
            // Board...
            // - PPL
            // More organizers
            // - PPL

            for (i in 0..3) {
                Column(modifier = Modifier.height(160.dp)) {
                    Text("TEAM $i")
                    // Horizontal list of people?
                    Box(modifier = Modifier.fillMaxSize()) {
                        // BowWithConstraints will provide the maxWidth used below
                        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                            // LazyRow to display your items horizontally
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState())
                            ) {
                                for (i2 in 0..5) {
                                    Card(
                                        modifier = Modifier
                                            .height(150.dp)
                                            .width(150.dp)
                                            .padding(10.dp)
                                    ) {
                                        Column {
                                            Text("IMAGE FOR $i2")
                                            Text("Person $i2")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(spacing))
            Button(
                onClick = { openURL(uriHandler, "https://discord.com/invite/HNqZPUy7An") },
            ) {
                CenteredText("Discord")
            }
            Divider()
            Button(
                onClick = { openURL(uriHandler, "https://www.facebook.com/Appsterdam/") }
            ) {
                CenteredText("Facebook")
            }
            Divider()
            Button(
                onClick = { openURL(uriHandler, "https://twitter.com/Appsterdam") }
            ) {
                CenteredText("Twitter")
            }
            Divider()
            Button(
                onClick = { openURL(uriHandler, "https://www.youtube.com/user/Appsterdam") }
            ) {
                CenteredText("YouTube")
            }
            Spacer(Modifier.height(spacing))
            Button(
                onClick = { openURL(uriHandler, "https://appsterdam.rs/code-of-conduct/") }
            ) {
                CenteredText("Code Of Conduct")
            }
            Spacer(Modifier.height(spacing))
            Button(
                onClick = { openURL(uriHandler, "https://appsterdam.rs/privacy-policy/") }
            ) {
                CenteredText("Privacy Policy")
            }
            Spacer(Modifier.height(spacing))
            CenteredText("© 2012-2022 Stichting Appsterdam. All rights reserved.")

            println("AboutView layout")
            println(mdState.value.toString())
        }
    }

//    @Composable
//    fun toast(text: String) {
//        val context = LocalContext.current
//
//        Toast.makeText(
//            context,
//            text,
//            Toast.LENGTH_SHORT
//        ).show()
//    }

    private fun openURL(context: UriHandler, url: String) {
        context.openUri(url)
    }

    @Composable
    fun CenteredText(text: String) {
        Text(
            text,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AboutViewPreview() {
    AppsterdamTheme {
        AboutView().Layout()
    }
}