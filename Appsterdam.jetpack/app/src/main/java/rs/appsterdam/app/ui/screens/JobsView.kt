package rs.appsterdam.app.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import rs.appsterdam.app.ui.theme.AppsterdamTheme
import androidx.compose.foundation.layout.*

class JobsView {
    init {
        println("APP: JobsView")
    }

    @Composable
    fun Layout() {
        Column {
            Text("JobsView")
            Spacer(Modifier.height(50.dp))
        }
    }

//    fun getJobs() {
//        // Get data from https://appsterdam.rs/api/jobs.json and display it
//
//    }
}

@Preview(showBackground = true)
@Composable
fun JobsViewPreview() {
    AppsterdamTheme {
        JobsView().Layout()
    }
}