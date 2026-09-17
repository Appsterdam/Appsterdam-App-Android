package rs.appsterdam.app.ui.screens.about

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import org.koin.androidx.compose.getViewModel
import rs.appsterdam.app.R
import rs.appsterdam.app.models.Member
import rs.appsterdam.app.models.Team
import rs.appsterdam.app.ui.theme.AppsterdamPrimary
import rs.appsterdam.app.ui.theme.AppsterdamTheme
import rs.appsterdam.app.ui.theme.Typography
import rs.appsterdam.app.utils.collectAsStateRepeatedly

class AboutView(val showBottomSheet: (sheet: @Composable (() -> Unit) -> Unit) -> Unit) {

    @Composable
    fun Layout() {
        val viewModel = getViewModel<AboutViewModel>()
        val state by viewModel.state.collectAsStateRepeatedly()
        AboutContent(state = state)
    }

    @Composable
    fun AboutContent(
        state: AboutViewModel.State
    ) = Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    ) {
        val uriHandler = LocalUriHandler.current
        val spacing = 16.dp

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.appsterdam_logo),
                contentScale = ContentScale.Fit,
                contentDescription = "Appsterdam Logo",
                modifier = Modifier.size(160.dp)
            )
        }

        Spacer(Modifier.height(spacing))
        CenteredText("Appsterdam", style = Typography.headlineMedium)

        val context: Context = LocalContext.current
        val info = context.packageManager?.
            getPackageInfo(
                context.packageName,
                0
            )
        CenteredText("Version: " + info?.versionName, style = Typography.bodySmall)

        Spacer(Modifier.height(spacing * 2))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "“If you want to make movies, go to Hollywood.\n" +
                            "If you want to make musicals, go to Broadway.\n" +
                            "If you want to make apps, go to Appsterdam.”",
                    style = Typography.bodyLarge,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "- Mike Lee",
                    style = Typography.labelLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Right
                )
            }
        }

        Spacer(modifier = Modifier.height(spacing * 2))
        Text(
            "Appsterdam Team",
            style = Typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.secondary
        )

        when (state) {
            is AboutViewModel.State.Success -> AboutTeamContent(state.teams)
            else -> LoadingContent()
        }

        Spacer(Modifier.height(spacing))
        
        SocialButtons(uriHandler)

        Spacer(Modifier.height(spacing))
        CenteredText(
            "© 2012-2026 Stichting Appsterdam. All rights reserved.",
            style = Typography.labelSmall
        )
        Spacer(Modifier.height(spacing))
    }

    @Composable
    fun SocialButtons(uriHandler: UriHandler) = Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val links = listOf(
            "Discord" to "https://discord.com/invite/HNqZPUy7An",
            "Facebook" to "https://www.facebook.com/Appsterdam/",
            "Twitter" to "https://twitter.com/Appsterdam",
            "YouTube" to "https://www.youtube.com/user/Appsterdam",
            "Code Of Conduct" to "https://appsterdam.rs/code-of-conduct/",
            "Privacy Policy" to "https://appsterdam.rs/privacy-policy/"
        )

        links.forEach { (label, url) ->
            Button(
                onClick = { openURL(uriHandler, url) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Text(label)
            }
        }
    }

    @Composable
    fun LoadingContent() = Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth().padding(32.dp)
    ) {
        CircularProgressIndicator(modifier = Modifier.size(32.dp))
    }

    @Composable
    fun AboutTeamContent(teams: List<Team>) = Column(
        modifier = Modifier.padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        teams.forEach { team ->
            TeamComposable(team)
        }
    }

    @Composable
    fun TeamComposable(team: Team) = ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(vertical = 16.dp)) {
            Text(
                "${team.teamName}",
                style = Typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 8.dp)
            ) {
                team.members.forEach { member ->
                    MemberCard(member)
                }
            }
        }
    }

    @Composable
    fun MemberCard(member: Member) {
        val uriHandler = LocalUriHandler.current
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .width(120.dp)
                .clickable {
                    showBottomSheet { onClose ->
                        AppsterdamTheme {
                            MemberDescriptionSheet(member, onClose) { url ->
                                openURL(uriHandler, url)
                            }
                        }
                    }
                }
        ) {
            GlideImage(
                imageModel = member.picture,
                contentScale = ContentScale.Crop,
                placeHolder = Icons.Rounded.Person,
                error = Icons.Rounded.Person,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "${member.name}",
                style = Typography.labelLarge,
                textAlign = TextAlign.Center,
                maxLines = 1,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                "${member.function}",
                style = Typography.labelSmall,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

private fun openURL(context: UriHandler, url: String) {
    context.openUri(url)
}

@Composable
fun CenteredText(text: String, style: TextStyle = LocalTextStyle.current) {
    Text(
        text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        textAlign = TextAlign.Center,
        style = style
    )
}

@Preview(showBackground = true)
@Composable
fun AboutViewPreview() {
    AppsterdamTheme {
        AboutView {}.AboutContent(
            state = AboutViewModel.State.Loading
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AboutViewSuccessPreview() {
    AppsterdamTheme {
        AboutView {}.AboutContent(
            state = AboutViewModel.State.Success(
                teams = listOf(
                    Team(
                        teamName = "Board",
                        members = arrayListOf(
                            Member(
                                name = "Mike Lee",
                                function = "Founder"
                            )
                        )
                    )
                )
            )
        )
    }
}
