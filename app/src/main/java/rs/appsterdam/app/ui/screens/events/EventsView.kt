package rs.appsterdam.app.ui.screens.events

import android.content.ClipData
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.graphics.Color
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import rs.appsterdam.app.models.Event
import rs.appsterdam.app.models.EventGroup
import rs.appsterdam.app.ui.theme.AppsterdamTheme
import rs.appsterdam.app.ui.theme.Typography
import rs.appsterdam.app.utils.collectAsStateRepeatedly
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun EventsLayout() {
    val viewModel = getViewModel<EventsViewModel>()
    val state by viewModel.state.collectAsStateRepeatedly()
    EventsContent(state)
    println("APP: EventsView layout")
}

@Composable
fun EventsContent(state: EventsViewModel.State) = when (state) {
    is EventsViewModel.State.Loading -> EventsLoading()
    is EventsViewModel.State.Success -> EventsSuccess(state.eventList)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventsSuccess(eventList: List<EventGroup>) = LazyColumn {
        eventList.map { eventGroup ->
            eventGroup.name?.let {
                    stickyHeader {
                            Text(
                                text = it,
                                style = Typography.headlineLarge,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.onSecondary)
                            )
                }
            }

            eventGroup.events?.map { event ->
                // This does not work if it is a LazyColumn
                item {
                    EventRow(event)
                }
            }
        }
}

@Composable
fun EventsLoading() = Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier.fillMaxSize()
) {
    CircularProgressIndicator()
}


@Composable
fun EventRow(event: Event) = Column {
    val context = LocalContext.current

    Row(Modifier.clickable {
        Toast.makeText(
            context,
            "Event: ${event.name}\nID: ${event.id}",
            Toast.LENGTH_SHORT
        ).show()
    }) {
        Column {
            Text(
                text = event.name.toString(),
                style = Typography.labelLarge,
                modifier = Modifier.fillMaxWidth()
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                val newDate = event.date?.split(":")?.get(0)
                val pattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
                val localDateTime = LocalDateTime.parse(newDate, pattern)
                val newPattern = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")

                Text(
                    text = "\uD83D\uDCC5 ${localDateTime.format(newPattern)}"
                )

                Text(
                    text = "\uD83E\uDEC2 ${event.attendees}",
                    textAlign = TextAlign.Right,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    Divider()
    Spacer(modifier = Modifier.height(1.dp))
}

@Preview(showBackground = true)
@Composable
fun EventsViewPreview() {
    AppsterdamTheme {
        EventsLayout()
    }
}

@Preview(showBackground = true)
@Composable
fun EventsRowPreview() {
    AppsterdamTheme {
        EventRow(
            event = Event(
                name = "NAME",
                description = "DESCRIPTION",
            )
        )
    }
}