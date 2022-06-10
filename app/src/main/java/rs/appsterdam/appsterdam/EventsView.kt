package rs.appsterdam.appsterdam

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import rs.appsterdam.appsterdam.ui.theme.AppsterdamTheme

class EventsView {
    init {
        println("EventsView")
    }

    @Composable
    fun layout() {
        Text("EventsView Contents")
        println("EventsView layout")
    }
}

@Preview(showBackground = true)
@Composable
fun EventsViewPreview() {
    AppsterdamTheme {
        EventsView().layout()
    }
}