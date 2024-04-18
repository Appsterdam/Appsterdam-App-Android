package rs.appsterdam.app.ui.screens.about

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
    ) = Column {
        val uriHandler = LocalUriHandler.current
        val spacing = 10.dp

        Spacer(Modifier.height(spacing + spacing))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.appsterdam_logo),
                contentScale = ContentScale.FillBounds,
                contentDescription = "Appsterdam Logo",
                modifier = Modifier.size(200.dp)
            )
        }

        Spacer(Modifier.height(spacing))
        CenteredText("Appsterdam", style = Typography.headlineMedium)

        var context: Context = LocalContext.current
        val info = context?.
            packageManager?.
            getPackageInfo(
                context.packageName,
                PackageManager.GET_ACTIVITIES
            )
        CenteredText("Version: " + info?.versionName)

        Spacer(Modifier.height(spacing + spacing))
        CenteredText(
            "“If you want to make movies, go to Hollywood.\n" +
                    "If you want to make musicals, go to Broadway.\n" +
                    "If you want to make apps, go to Appsterdam.”"
        )
        Text(
            "- Mike Lee ",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.Right
        )
        Spacer(Modifier.height(spacing * 2))
        Text(
            "Appsterdam Team",
            style = Typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        )

        when (state) {
            is AboutViewModel.State.Success -> AboutTeamContent(state.teams)
            else -> LoadingContent()
        }

        Spacer(Modifier.height(spacing))
        ColouredButton(
            onClick = { openURL(uriHandler, "https://discord.com/invite/HNqZPUy7An") },
        ) {
            CenteredText("Discord")
        }
        Divider()
        ColouredButton(
            onClick = { openURL(uriHandler, "https://www.facebook.com/Appsterdam/") }
        ) {
            CenteredText("Facebook")
        }
        Divider()
        ColouredButton(
            onClick = { openURL(uriHandler, "https://twitter.com/Appsterdam") }
        ) {
            CenteredText("Twitter")
        }
        Divider()
        ColouredButton(
            onClick = { openURL(uriHandler, "https://www.youtube.com/user/Appsterdam") }
        ) {
            CenteredText("YouTube")
        }
        Spacer(Modifier.height(spacing))
        ColouredButton(
            onClick = { openURL(uriHandler, "https://appsterdam.rs/code-of-conduct/") }
        ) {
            CenteredText("Code Of Conduct")
        }
        Divider()
        ColouredButton(
            onClick = { openURL(uriHandler, "https://appsterdam.rs/privacy-policy/") }
        ) {
            CenteredText("Privacy Policy")
        }
        Spacer(Modifier.height(spacing))
        CenteredText("© 2012-2023 Stichting Appsterdam. All rights reserved.")
        Spacer(Modifier.height(spacing))
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
            .padding(vertical = 8.dp)
    ) {
        teams.forEach { team ->
            TeamComposable(team)
            Spacer(Modifier.height(16.dp))
        }
    }

    @Composable
    fun TeamComposable(team: Team) = Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(12.dp))
            .padding(vertical = 12.dp)
    ) {
        Text(
            "${team.teamName}",
            style = Typography.labelLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
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
    fun MemberCard(member: Member) {
        val uriHandler = LocalUriHandler.current
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 4.dp)
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
                    .size(140.dp)
                    .padding(10.dp)
                    .clip(CircleShape)
            )
            Text("${member.name}", style = Typography.bodyLarge.copy(color = AppsterdamPrimary))
            Text("${member.function}", style = Typography.labelSmall)
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

@Composable
fun ColouredButton(
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) = Button(
    onClick = onClick,
    colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary
    ),
    content = content,
    modifier = Modifier.padding(horizontal = 16.dp)
)


@Preview(showBackground = true)
@Composable
fun AboutViewPreview() {
    AppsterdamTheme {
        AboutView {}.Layout()
    }
}