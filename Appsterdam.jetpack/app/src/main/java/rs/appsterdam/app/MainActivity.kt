package rs.appsterdam.app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import rs.appsterdam.app.di.viewModelModule
import rs.appsterdam.app.di.networkModule
import rs.appsterdam.app.ui.screens.events.EventsView
import rs.appsterdam.app.ui.screens.jobs.JobsView
import rs.appsterdam.app.ui.screens.about.AboutView
import rs.appsterdam.app.ui.screens.home.HomeView
import rs.appsterdam.app.ui.theme.AppsterdamTheme
import rs.appsterdam.app.utils.showAsBottomSheet

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@MainActivity)
            modules(
                listOf(viewModelModule, networkModule),
            )
        }
        setContent {
            AppsterdamTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
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

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TabView(showBottomSheet: (sheet: @Composable (() -> Unit) -> Unit) -> Unit) {
    var tabIndex by remember {
        mutableIntStateOf(0)
    }

    val tabTitles = listOf(
        "Home",
        "Events",
        "Jobs",
        "About",
    )

    val tabIcons = listOf(
        R.drawable.home,
        R.drawable.events,
        R.drawable.jobs,
        R.drawable.about,
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = tabTitles[tabIndex],
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.secondary,
                ),
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.onSecondary,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                tabTitles.forEachIndexed { index, title ->
                    NavigationBarItem(
                        selected = tabIndex == index,
                        onClick = {
                            tabIndex = index
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = tabIcons[index]),
                                contentDescription = title,
                                tint = if (tabIndex == index) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                            )
                        },
                        label = {
                            Text(text = title)
                        },
                    )
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            when (tabTitles[tabIndex]) {
                "Home" -> HomeView().Layout()
                "Events" -> EventsView(showBottomSheet).Layout()
                "Jobs" -> JobsView(showBottomSheet).Layout()
                "About" -> AboutView(showBottomSheet).Layout()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppsterdamTheme {
        TabView {}
    }
}