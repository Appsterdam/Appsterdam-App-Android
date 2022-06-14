package rs.appsterdam.app

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import rs.appsterdam.app.ui.theme.AppsterdamTheme

class EventsView {
    init {
        println("AAPP: EventsView")
    }

    @Composable
    fun eventRow(eventName: String) {
        val context = LocalContext.current

        Row(Modifier.clickable {
            Toast.makeText(
                context,
                "Event ${eventName} clicked",
                Toast.LENGTH_SHORT
            ).show()
        }) {
            Column {
                Text(
                    text = "Event: $eventName",
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Subrow: $eventName",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Divider()
    }

    @Composable
    fun layout() {
        Column {
            for (index in 0..100) {
                eventRow("Event $index")
            }
        }

        println("AAPP: EventsView layout")
    }

    @Composable
    fun eventDetailView() {

    }
}

@Preview(showBackground = true)
@Composable
fun EventsViewPreview() {
    AppsterdamTheme {
        EventsView().layout()
    }
}