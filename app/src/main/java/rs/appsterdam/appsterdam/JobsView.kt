package rs.appsterdam.appsterdam

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import rs.appsterdam.appsterdam.ui.theme.AppsterdamTheme
import androidx.compose.*
import androidx.compose.foundation.layout.*

class JobsView {
    init {
        println("JobsView")
    }

    @Composable
    fun layout() {
        Column(
            modifier = Modifier
                .width(100.dp).fillMaxHeight()
//                .fillMaxWidth(),
//            Arrangement.SpaceBetween
        ) {
            Text("JobsView")
            Spacer(Modifier.height(50.dp))
            Text("JobsView")
            println("JobsView layout")
        }
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