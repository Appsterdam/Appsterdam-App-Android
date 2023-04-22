package rs.appsterdam.app.ui.screens.events

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.getViewModel
import rs.appsterdam.app.models.Event
import rs.appsterdam.app.models.EventGroup
import rs.appsterdam.app.ui.theme.AppsterdamTheme
import rs.appsterdam.app.utils.collectAsStateRepeatedly


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

@Composable
fun EventsSuccess(eventList: List<EventGroup>) = Column {
    //TODO this column can be converted to a LazyColumn.
    // However, this will cause a crash, as this Composable is nested within
    // 'verticalScroll' Composable.
    // So, to fix. You would need to remove the 'verticalScroll' from main activity,
    // and then make each 'tab' have its own verticalScroll modifier.
    // (except this tab, because the LazyColumn will provide its own scrolling)
    eventList.map { eventGroup ->
        eventGroup.name?.let {
            Text(text = it)
        }
        eventGroup.events?.map { event ->
            EventRow(event)
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
            "Event ${event.name} clicked",
            Toast.LENGTH_SHORT
        ).show()
    }) {
        Column {
            Text(
                text = "Event: ${event.name}",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    Divider()
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