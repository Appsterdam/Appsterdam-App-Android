package rs.appsterdam.appsterdam

import android.os.Bundle
import android.widget.ScrollView
import android.widget.TableLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import rs.appsterdam.appsterdam.ui.theme.*

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
    var tabIndex by remember {
        mutableStateOf(0)
    }

    val tabTitles = listOf(
        "Home",
        "Events",
        "Jobs",
        "About"
    )

    Column {
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .weight(weight=1f, fill = true)) {
            when (tabTitles[tabIndex]) {
                "Home" -> HomeView().layout()
                "Events" -> EventsView().layout()
                "Jobs" -> JobsView().layout()
                "About" -> AboutView().layout()
            }
        }

        TabRow(selectedTabIndex = tabIndex,
            contentColor = MaterialTheme.colorScheme.primary,
            backgroundColor = MaterialTheme.colorScheme.onSecondary
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = tabIndex == index,
                    onClick = {
                        println("clicked on $title, index: $index")
                        tabIndex = index
                              },
                    icon = {
                           Text("Icon")
                    },
                    text = {
                        Text(text = title)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppsterdamTheme {
        tabs()
    }
}