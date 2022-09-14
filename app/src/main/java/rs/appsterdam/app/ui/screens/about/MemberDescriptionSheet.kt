package rs.appsterdam.app.ui.screens.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import dev.jeziellago.compose.markdowntext.MarkdownText
import rs.appsterdam.app.R
import rs.appsterdam.app.models.Member
import rs.appsterdam.app.ui.theme.AppsterdamPrimary
import rs.appsterdam.app.ui.theme.BackgroundSecondary
import rs.appsterdam.app.ui.theme.Typography

@Composable
fun MemberDescriptionSheet(
    member: Member,
    onClose: () -> Unit,
    openURL: (url: String) -> Unit,
) = Column(
    modifier = Modifier
        .fillMaxWidth()
) {
    SheetHeader(member.name, member.function, onClose)
    SheetContent(member, openURL)
}


@Composable
fun SheetHeader(
    name: String?,
    function: String?,
    onClose: () -> Unit
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(topStartPercent = 25, topEndPercent = 25))
        .background(color = Color.White),
    horizontalArrangement = Arrangement.SpaceBetween,
) {
    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
        Text(name ?: "", style = Typography.labelLarge)
        Text(function ?: "", style = Typography.bodyLarge)
    }
    IconButton(onClick = onClose) {
        Icon(imageVector = Icons.Rounded.Close, contentDescription = "Close member description")
    }
}

@Composable
fun SheetContent(member: Member, openURL: (url: String) -> Unit) = LazyColumn(
    modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.White),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    item {
        Spacer(modifier = Modifier.height(32.dp))
        GlideImage(
            imageModel = member.picture,
            contentScale = ContentScale.Crop,
            placeHolder = Icons.Rounded.Person,
            error = Icons.Rounded.Person,
            modifier = Modifier
                .size(200.dp)
                .padding(10.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
    item {
        MemberLinks(member.twitter, member.linkedin, member.website, openURL)
        Spacer(modifier = Modifier.height(16.dp))
    }
    item {
        BioMarkdown(bio = member.bio ?: "…")
    }
}

@Composable
fun MemberLinks(
    twitter: String?,
    linkedIn: String?,
    website: String?,
    openURL: (url: String) -> Unit
) = Row(
    horizontalArrangement = Arrangement.SpaceEvenly,
    modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 64.dp)
) {
    if (!twitter.isNullOrBlank()) {
        IconButton(
            onClick = { openURL("https://twitter.com/$twitter") }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.twitter),
                contentDescription = "twitter link",
                tint = AppsterdamPrimary
            )
        }
    }
    if (!linkedIn.isNullOrBlank()) {
        IconButton(
            onClick = { openURL("https://linkedin.com/in/$linkedIn") }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.linkedin),
                contentDescription = "linkedIn link",
                tint = AppsterdamPrimary
            )
        }
    }
    if (!website.isNullOrBlank()) {
        IconButton(
            onClick = { openURL(website) }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.globe),
                contentDescription = "website link",
                tint = AppsterdamPrimary,
            )
        }
    }
}

@Composable
fun BioMarkdown(bio: String) = Box(
    modifier = Modifier
        .padding(16.dp)
        .background(BackgroundSecondary, RoundedCornerShape(8.dp))
        .padding(16.dp)
) {
    MarkdownText(
        bio
            // Markdown editor ignores one line break, so we need 2
            .replace("\n", "\n\n"),
        fontSize = 18.sp,
        modifier = Modifier.fillMaxWidth()
    )
}