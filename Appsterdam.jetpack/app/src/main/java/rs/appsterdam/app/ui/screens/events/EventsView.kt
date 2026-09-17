package rs.appsterdam.app.ui.screens.events

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import rs.appsterdam.app.models.Event
import rs.appsterdam.app.models.EventGroup
import rs.appsterdam.app.ui.theme.AppsterdamTheme
import rs.appsterdam.app.ui.theme.Typography
import rs.appsterdam.app.utils.DateUtils
import rs.appsterdam.app.utils.collectAsStateRepeatedly

class EventsView(val showBottomSheet: (sheet: @Composable (() -> Unit) -> Unit) -> Unit) {

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun Layout() {
        val viewModel = getViewModel<EventsViewModel>()
        val state by viewModel.state.collectAsStateRepeatedly()
        EventsContent(state)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun EventsContent(state: EventsViewModel.State) = when (state) {
        is EventsViewModel.State.Loading -> EventsLoading()
        is EventsViewModel.State.Success -> EventsSuccess(state.eventList)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun EventsSuccess(eventList: List<EventGroup>) = LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        eventList.forEach { eventGroup ->
            eventGroup.name?.let {
                stickyHeader {
                    Text(
                        text = it,
                        style = Typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(vertical = 8.dp),
                    )
                }
            }

            items(eventGroup.events ?: emptyList()) { event ->
                EventRow(event)
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    @Composable
    fun EventsLoading() = Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        CircularProgressIndicator()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun EventRow(event: Event) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showBottomSheet { onClose ->
                        AppsterdamTheme {
                            EventDescriptionSheet(event, onClose)
                        }
                    }
                },
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = event.name.toString(),
                    style = Typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "\uD83D\uDCC5 ${DateUtils.formatEventDate(event.date)}",
                        style = Typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )

                    Text(
                        text = "\uD83E\uDEC2 ${event.attendees}",
                        style = Typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
                
                event.location_name?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "\uD83D\uDCCD $it",
                        style = Typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EventsViewPreview() {
    AppsterdamTheme {
        EventsView {}.EventsContent(
            state = EventsViewModel.State.Loading
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EventsViewSuccessPreview() {
    AppsterdamTheme {
        EventsView {}.EventsContent(
            state = EventsViewModel.State.Success(
                eventList = listOf(
                    EventGroup(
                        name = "Upcoming Events",
                        events = arrayListOf(
                            Event(
                                name = "Weekly Coffee",
                                description = "Weekly coffee meet"
                            )
                        )
                    )
                )
            )
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EventsRowPreview() {
    AppsterdamTheme {
        EventsView {}.EventRow(
            event = Event(
                name = "NAME",
                description = "DESCRIPTION",
            )
        )
    }
}
