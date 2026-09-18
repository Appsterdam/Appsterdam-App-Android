package rs.appsterdam.app.ui.screens.jobs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jeziellago.compose.markdowntext.MarkdownText
import rs.appsterdam.app.models.Jobs
import rs.appsterdam.app.ui.theme.Typography

@Composable
fun JobDescriptionSheet(
    job: Jobs,
    onClose: () -> Unit,
) = Column(
    modifier = Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colorScheme.tertiary),
) {
    JobSheetHeader(job, onClose)
    JobSheetContent(
        job = job,
        modifier = Modifier.weight(1f)
    )

    // Apply Button always visible at the bottom
    val uriHandler = LocalUriHandler.current
    job.jobUrl?.let { url ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 24.dp)
        ) {
            Button(
                onClick = { uriHandler.openUri(url) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Text("Apply for this job")
            }
        }
    }
}

@Composable
fun JobSheetHeader(
    job: Jobs,
    onClose: () -> Unit,
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 8.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
) {
    Column(modifier = Modifier.weight(1f)) {
        Text(
            job.JobTitle ?: "",
            style = Typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            job.JobProvider ?: "",
            style = Typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
    IconButton(onClick = onClose) {
        Icon(
            imageVector = Icons.Rounded.Close,
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = "Close job description"
        )
    }
}

@Composable
fun JobSheetContent(
    job: Jobs,
    modifier: Modifier = Modifier
) = LazyColumn(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    item {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // City and Provider
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "\uD83D\uDCCD ${job.JobCity}",
                    style = Typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Dates
            Text(
                text = "Published: ${job.JobPublishStartDate} to ${job.JobPublishEndDate}",
                style = Typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Spacer(modifier = Modifier.height(24.dp))

            // Description
            Text(
                text = "Description",
                style = Typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))

            JobBioMarkdown(
                job.JobDescription ?: job.JobShortDescription ?: "No description provided."
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun JobBioMarkdown(bio: String) = Box(
    modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp))
        .padding(16.dp)
) {
    MarkdownText(
        markdown = bio.replace("\n", "\n\n"),
        style = androidx.compose.ui.text.TextStyle(
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun JobDescriptionSheetPreview() {
    val sampleJob = Jobs(
        jobID = "1",
        jobUrl = "https://appsterdam.rs",
        JobTitle = "Senior Android Developer",
        JobShortDescription = "Join our team to build amazing Android apps using Jetpack Compose.",
        JobDescription = """
            # About the Role
            We are looking for a Senior Android Developer to join our team. You will be responsible for developing new features and maintaining existing ones.
            
            ## Requirements
            - 5+ years of experience in Android development
            - Strong knowledge of Kotlin and Jetpack Compose
            - Experience with Clean Architecture and MVVM
            
            ## What we offer
            - Competitive salary
            - Flexible working hours
            - Remote work options
        """.trimIndent(),
        JobPublishStartDate = "2023-01-01",
        JobPublishEndDate = "2023-12-31",
        JobProvider = "Appsterdam.RS",
        JobCity = "Amsterdam, Noord-Holland, The Netherlands"
    )

    rs.appsterdam.app.ui.theme.AppsterdamTheme {
        JobDescriptionSheet(
            job = sampleJob,
            onClose = {}
        )
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun JobDescriptionSheetDarkPreview() {
    val sampleJob = Jobs(
        jobID = "1",
        jobUrl = "https://appsterdam.rs",
        JobTitle = "Senior Android Developer",
        JobShortDescription = "Join our team to build amazing Android apps using Jetpack Compose.",
        JobDescription = "Full job description goes here.",
        JobPublishStartDate = "2023-01-01",
        JobPublishEndDate = "2023-12-31",
        JobProvider = "Appsterdam.RS",
        JobCity = "Amsterdam, Noord-Holland, The Netherlands"
    )

    rs.appsterdam.app.ui.theme.AppsterdamTheme(darkTheme = true) {
        JobDescriptionSheet(
            job = sampleJob,
            onClose = {}
        )
    }
}