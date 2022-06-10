package rs.appsterdam.appsterdam

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import rs.appsterdam.appsterdam.ui.theme.AppsterdamTheme

class JobsView {
    init {
        println("JobsView")
    }

    @Composable
    fun layout() {
        Text("JobsView")
        println("JobsView layout")
    }

    fun getJobs() {
        // Get data from https://appsterdam.rs/api/jobs.json and display it

    }
}

@Preview(showBackground = true)
@Composable
fun JobsViewPreview() {
    AppsterdamTheme {
        JobsView().layout()
    }
}