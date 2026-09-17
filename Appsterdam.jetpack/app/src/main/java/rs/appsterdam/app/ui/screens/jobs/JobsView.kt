package rs.appsterdam.app.ui.screens.jobs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import rs.appsterdam.app.models.Jobs
import rs.appsterdam.app.ui.theme.AppsterdamTheme
import rs.appsterdam.app.ui.theme.Typography
import rs.appsterdam.app.utils.collectAsStateRepeatedly

class JobsView(val showBottomSheet: (sheet: @Composable (() -> Unit) -> Unit) -> Unit) {

    @Composable
    fun Layout() {
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

    @Composable
    fun JobsSuccess(jobList: List<Jobs>) = LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(jobList) { job ->
            JobRow(job)
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    @Composable
    fun JobsLoading() = Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        CircularProgressIndicator()
    }


    @Composable
    fun JobRow(job: Jobs) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showBottomSheet { onClose ->
                        AppsterdamTheme {
                            JobDescriptionSheet(job, onClose)
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
                    text = job.JobTitle.toString(),
                    style = Typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                job.JobShortDescription?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it,
                        style = Typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "\uD83D\uDCCD ${job.JobCity}",
                        style = Typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )

                    Text(
                        text = "\uD83C\uDFE0 ${job.JobProvider}",
                        style = Typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Right,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JobsViewPreview() {
    AppsterdamTheme {
        JobsView {}.JobsContent(
            state = JobsViewModel.State.Loading
        )
    }
}

@Preview(showBackground = true)
@Composable
fun JobsViewSuccessPreview() {
    AppsterdamTheme {
        JobsView {}.JobsContent(
            state = JobsViewModel.State.Success(
                jobsList = listOf(
                    Jobs(
                        JobTitle = "Android Developer",
                        JobCity = "Amsterdam",
                        JobProvider = "Appsterdam",
                        JobShortDescription = "Looking for a great Android developer."
                    )
                )
            )
        )
    }
}
