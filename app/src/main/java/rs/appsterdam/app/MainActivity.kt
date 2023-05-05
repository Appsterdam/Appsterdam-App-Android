package rs.appsterdam.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.icons.*
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import rs.appsterdam.app.di.viewModelModule
import rs.appsterdam.app.ui.screens.about.AboutView
import rs.appsterdam.app.ui.screens.events.EventsLayout
import rs.appsterdam.app.ui.screens.home.HomeView
import rs.appsterdam.app.ui.screens.jobs.JobsLayout
import rs.appsterdam.app.ui.theme.AppsterdamTheme
import rs.appsterdam.app.utils.showAsBottomSheet

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@MainActivity)
            modules(
                listOf(viewModelModule)
            )
        }
        setContent {
            AppsterdamTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TabView { sheet ->
                        showAsBottomSheet { onClose ->
                            sheet(onClose)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        GlobalContext.stopKoin()
    }
}

@Composable
fun TabView(showBottomSheet: (sheet: @Composable (() -> Unit) -> Unit) -> Unit) {
    var tabIndex by remember {
        mutableStateOf(0)
    }

    val tabTitles = listOf(
        "Home",
        "Events",
        "Jobs",
        "About"
    )

    val tabIcons = listOf<Painter>( // cannot use this?
        painterResource(R.drawable.home),
        painterResource(R.drawable.events),
        painterResource(R.drawable.jobs),
        painterResource(R.drawable.info)
    )

    Column {
        Column(
            modifier = Modifier
                .weight(weight = 1f, fill = true)
        ) {
            when (tabTitles[tabIndex]) {
                "Home" -> HomeView().Layout()
                "Events" -> EventsLayout()
                "Jobs" -> JobsLayout()
                "About" -> AboutView(showBottomSheet).Layout()
            }
        }

        TabRow(
            selectedTabIndex = tabIndex,
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
                        Icon(
                            painter = tabIcons[index],
                            contentDescription = title
                        )
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
        TabView {}
    }
}