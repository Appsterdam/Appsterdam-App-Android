package rs.appsterdam.appsterdam

import android.os.Bundle
import android.widget.TableLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import rs.appsterdam.appsterdam.ui.theme.AppsterdamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppsterdamTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    tabs()
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello From $name!")
}

@Composable
fun tabs() {
    var tabIndex: Int = 0
//    var tabIndex by remember {
//        mutableStateOf(0)
//    }

    val tabTitles = listOf(
        "Home",
        "Events",
        "Jobs",
        "About"
    )

    Column {
        TabRow(selectedTabIndex = tabIndex) {
            tabTitles.forEachIndexed { index, title ->
                Tab(selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    text = { Text(text = title) })
            }
        }
        when (tabIndex) {
            0 -> Text("Home content")
            1 -> Text("Events content")
            2 -> Text("Jobs content")
            3 -> Text(text = "About content")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppsterdamTheme {
        Greeting("Android")
    }
}