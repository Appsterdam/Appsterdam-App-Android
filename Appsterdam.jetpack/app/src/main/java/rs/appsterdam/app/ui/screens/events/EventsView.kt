package rs.appsterdam.app.ui.screens.events

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
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

class EventsView {

    @Composable
    fun Layout() {
        val viewModel = getViewModel<EventsViewModel>()
        val state by viewModel.state.collectAsStateRepeatedly()
        EventsContent(state)
    }

    @Composable
    fun EventsContent(state: EventsViewModel.State) = when (state) {
        is EventsViewModel.State.Loading -> EventsLoading()
        is EventsViewModel.State.Success -> EventsSuccess(state.eventList)
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun EventsSuccess(eventList: List<EventGroup>) = Column {
        eventList.map { eventGroup ->
            eventGroup.name?.let {
                // stickyHeader Requires LazyColumn but that doesn't work.
                // Then we get a "Vertically scrollable component was
                // measured with an infinity maximum height constraints,
                // which is disallowed." error.
//                stickyHeader {
                    Text(
                        text = it,
                        style = Typography.headlineLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.onSecondary)
                    )
//                }
            }

            eventGroup.events?.map { event ->
                EventRow(event)
            }

            Spacer(modifier = Modifier.height(25.dp))
        }
    }

    @Composable
    fun EventsLoading() = Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun EventRow(event: Event) = Column {
        val context = LocalContext.current

        Row(
            Modifier
                .clickable {
                    Toast
                        .makeText(
                            context,
                            "Event: ${event.name}\nID: ${event.id}",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
                .padding(horizontal = 5.dp)
        ) {
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
        Spacer(modifier = Modifier.height(2.dp))
    }

    @Preview(showBackground = true)
    @Composable
    fun EventsViewPreview() {
        AppsterdamTheme {
            Layout()
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
}
