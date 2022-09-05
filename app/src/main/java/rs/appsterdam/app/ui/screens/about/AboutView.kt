package rs.appsterdam.app.ui.screens.about

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import org.koin.androidx.compose.getViewModel
import rs.appsterdam.app.BuildConfig
import rs.appsterdam.app.R
import rs.appsterdam.app.models.Member
import rs.appsterdam.app.models.Team
import rs.appsterdam.app.ui.theme.AppsterdamTheme
import rs.appsterdam.app.ui.theme.BackgroundSecondary
import rs.appsterdam.app.utils.collectAsStateRepeatedly

class AboutView {

    @Composable
    fun Layout() {
        val viewModel = getViewModel<AboutViewModel>()
        val state by viewModel.state.collectAsStateRepeatedly()
        AboutContent(state = state)
    }

    @Composable
    fun AboutContent(state: AboutViewModel.State) = Column {
        val uriHandler = LocalUriHandler.current
        val spacing = 10.dp

        Spacer(Modifier.height(spacing + spacing))

        Image(
            painter = painterResource(R.drawable.appsterdam_logo),
            contentDescription = "Appsterdam Logo",
            modifier = Modifier
                .height(
                    Resources
                        .getSystem()
                        .getDisplayMetrics()
                        .widthPixels.dp / 5
                )
                .fillMaxWidth()
        )

        Spacer(Modifier.height(spacing))
        CenteredText("Appsterdam")
        CenteredText("Version: " + BuildConfig.VERSION_NAME)
        Spacer(Modifier.height(spacing + spacing))
        CenteredText(
            "“If you want to make movies, go to Hollywood.\n" +
                    "If you want to make musicals, go to Broadway.\n" +
                    "If you want to make apps, go to Appsterdam.”"
        )
        Text(
            "- Mike Lee ",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Right
        )
        Spacer(Modifier.height(spacing * 2))
        Text("Appsterdam Team")
        // Board...
        // - PPL
        // More organizers
        // - PPL

        when (state) {
            is AboutViewModel.State.Success -> AboutTeamContent(state.teams)
            else -> LoadingContent()
        }

        Spacer(Modifier.height(spacing))
        Button(
            onClick = { openURL(uriHandler, "https://discord.com/invite/HNqZPUy7An") },
        ) {
            CenteredText("Discord")
        }
        Divider()
        Button(
            onClick = { openURL(uriHandler, "https://www.facebook.com/Appsterdam/") }
        ) {
            CenteredText("Facebook")
        }
        Divider()
        Button(
            onClick = { openURL(uriHandler, "https://twitter.com/Appsterdam") }
        ) {
            CenteredText("Twitter")
        }
        Divider()
        Button(
            onClick = { openURL(uriHandler, "https://www.youtube.com/user/Appsterdam") }
        ) {
            CenteredText("YouTube")
        }
        Spacer(Modifier.height(spacing))
        Button(
            onClick = { openURL(uriHandler, "https://appsterdam.rs/code-of-conduct/") }
        ) {
            CenteredText("Code Of Conduct")
        }
        Spacer(Modifier.height(spacing))
        Button(
            onClick = { openURL(uriHandler, "https://appsterdam.rs/privacy-policy/") }
        ) {
            CenteredText("Privacy Policy")
        }
        Spacer(Modifier.height(spacing))
        CenteredText("© 2012-2022 Stichting Appsterdam. All rights reserved.")

        println("AboutView layout")
    }

    @Composable
    fun LoadingContent() = Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(modifier = Modifier.size(32.dp))
    }

    @Composable
    fun AboutTeamContent(teams: List<Team>) = Column(
        modifier = Modifier
            .background(BackgroundSecondary, RoundedCornerShape(12.dp))
            .padding(vertical = 8.dp)
    ) {
        teams.forEach { team ->
            TeamComposable(team)
        }
    }

    @Composable
    fun TeamComposable(team: Team) = Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("${team.teamName}")
        // Horizontal list of people?
        Box(modifier = Modifier.fillMaxSize()) {
            // BowWithConstraints will provide the maxWidth used below
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                // LazyRow to display your items horizontally
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                ) {
                    team.members.forEach { member ->
                        MemberCard(member)
                    }
                }
            }
        }
    }

    @Composable
    fun MemberCard(member: Member) = Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        GlideImage(
            imageModel = member.picture,
            contentScale = ContentScale.Crop,
            placeHolder = Icons.Rounded.Person,
            error = Icons.Rounded.Person,
            modifier = Modifier
                .height(140.dp)
                .width(140.dp)
                .padding(10.dp)
                .clip(CircleShape)
        )
        Text("${member.name}")
        Text("${member.function}")
    }
}

private fun openURL(context: UriHandler, url: String) {
    context.openUri(url)
}

@Composable
fun CenteredText(text: String) {
    Text(
        text,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}


@Preview(showBackground = true)
@Composable
fun AboutViewPreview() {
    AppsterdamTheme {
        AboutView().Layout()
    }
}