package rs.appsterdam.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import rs.appsterdam.app.ui.theme.AppsterdamTheme

class _EventsView {
    init {
        println("APP: EventsView")
    }

    @Composable
    fun EventRow(eventName: String) {
        val context = LocalContext.current

        Row(Modifier.clickable {
            Toast.makeText(
                context,
                "Event $eventName clicked",
                Toast.LENGTH_SHORT
            ).show()
        }) {
            Column {
                Text(
                    text = "Event: $eventName",
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Sub row: $eventName",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Divider()
    }

    @Composable
    fun Layout() {
        Column {
            for (index in 0..50) {
                EventRow("Event $index")
            }
        }

        println("APP: EventRow layout")
    }

//    @Composable
//    fun EventDetailView() {
//
//    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun EventsViewPreview() {
//    AppsterdamTheme {
//        EventsView().Layout()
//    }
//}