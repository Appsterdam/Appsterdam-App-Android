package rs.appsterdam.app.ui.screens.jobs

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import rs.appsterdam.app.ui.theme.AppsterdamTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import org.koin.androidx.compose.getViewModel
import rs.appsterdam.app.ui.theme.Typography
import rs.appsterdam.app.utils.collectAsStateRepeatedly
import rs.appsterdam.app.models.Jobs

@Composable
fun JobsLayout() {
    val viewModel = getViewModel<JobsViewModel>()
    val state by viewModel.state.collectAsStateRepeatedly()
    JobsContent(state)
    println("APP: Jobs layout")
}

@Composable
fun JobsContent(state: JobsViewModel.State) = when (state) {
    is JobsViewModel.State.Loading -> JobsLoading()
    is JobsViewModel.State.Success -> JobsSuccess(state.jobsList)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JobsSuccess(jobList: List<Jobs>) = LazyColumn() {
    jobList.map { job ->
        item {
            JobRow(job)
        }
    }
}

@Composable
fun JobsLoading() = Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier.fillMaxSize()
) {
    CircularProgressIndicator()
}


@Composable
fun JobRow(job: Jobs) = Column {
    val context = LocalContext.current

    Row(Modifier.clickable {
        Toast.makeText(
            context,
            "Job: ${job.JobTitle}\nID: ${job.jobID}",
            Toast.LENGTH_SHORT
        ).show()
    }
        .padding(horizontal = 5.dp)
    ) {
        Column() {
            Text(
                text = job.JobTitle.toString(),
                style = Typography.labelLarge,
                modifier = Modifier.fillMaxWidth()
            )
            
            Row() {
                // pin, location
                // house, provider
                Text(
                    text = "\uD83D\uDCCD ${job.JobCity}"
                )

                Text(
                    text = "\uD83C\uDFE0 ${job.JobProvider}",
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
        JobsLayout()
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun EventsRowPreview() {
//    AppsterdamTheme {
//        JobRow(
//            event = Event(
//                name = "NAME",
//                description = "DESCRIPTION",
//            )
//        )
//    }
//}