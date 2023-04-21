package rs.appsterdam.app.ui.screens.home

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jeziellago.compose.markdowntext.MarkdownText
import org.koin.androidx.compose.getViewModel
import rs.appsterdam.app.R
import rs.appsterdam.app.ui.theme.AppsterdamTheme
import rs.appsterdam.app.utils.collectAsStateRepeatedly

class HomeView {
    @Composable
    fun Layout() {
        val viewModel = getViewModel<HomeViewModel>()
        val state by viewModel.state.collectAsStateRepeatedly()
        HomeContent(state = state)
    }

    @Composable
    fun HomeContent(state: HomeViewModel.State) = Column {
        val spacing = 10.dp

        Spacer(Modifier.height(spacing + spacing))

        Image(
            painter = painterResource(R.drawable.appsterdam_logo),
            contentDescription = "Appsterdam Logo",
            modifier = Modifier
                .height(
                    Resources
                        .getSystem()
                        .displayMetrics
                        .widthPixels.dp / 5
                )
                .fillMaxWidth()
        )

        Spacer(Modifier.height(spacing))

        when (state) {
            is HomeViewModel.State.Success -> SuccessContent(markdown = state.markdown)
            else -> LoadingContent()
        }

    }

    @Composable
    fun LoadingContent() = Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(modifier = Modifier.size(32.dp))
    }

    @Composable
    fun SuccessContent(markdown: String) {
        // Text color needs to change on dark mode status
        val textColor: Color = if (isSystemInDarkTheme()) Color.White else Color.Black

        MarkdownText(
            markdown
                    // Markdown editor ignores one line break, so we need 2
                    .replace("\n", "\n\n"),
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth(),
                color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewLoadingPreview() {
    AppsterdamTheme {
        HomeView().HomeContent(state = HomeViewModel.State.Loading)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewSuccessPreview() {
    AppsterdamTheme {
        HomeView().HomeContent(
            state = HomeViewModel.State.Success(
                markdown = "# HomePage Content"
            )
        )
    }
}