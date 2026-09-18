package rs.appsterdam.app.ui.screens.events

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import dev.jeziellago.compose.markdowntext.MarkdownText
import androidx.core.net.toUri
import rs.appsterdam.app.models.Event
import rs.appsterdam.app.ui.theme.Typography
import rs.appsterdam.app.utils.DateUtils
import android.os.Build
import androidx.annotation.RequiresApi
import android.annotation.SuppressLint

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventDescriptionSheet(
    event: Event,
    onClose: () -> Unit,
) = Column(
    modifier = Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colorScheme.tertiary),
) {
    EventSheetHeader(event, onClose)
    EventSheetContent(
        event = event,
        modifier = Modifier.weight(1f)
    )

    // Attend Button always visible at the bottom
    val context = LocalContext.current
    event.id?.let { eventId ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 24.dp)
        ) {
            Button(
                onClick = {
                    val urlStr = "Appsterdam/events/$eventId/"
                    val deeplinkIntent = Intent(Intent.ACTION_VIEW, "meetup://$urlStr".toUri())
                    val webIntent = Intent(Intent.ACTION_VIEW, "https://www.meetup.com/$urlStr".toUri())
                    
                    try {
                        context.startActivity(deeplinkIntent)
                    } catch (_: Exception) {
                        context.startActivity(webIntent)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Text("Attend")
            }
        }
    }
}

@Composable
fun EventSheetHeader(
    event: Event,
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
            event.name ?: "",
            style = Typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary
        )
        event.organizer?.let {
            Text(
                "Organizer: $it",
                style = Typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
    IconButton(onClick = onClose) {
        Icon(
            imageVector = Icons.Rounded.Close,
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = "Close event description"
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventSheetContent(
    event: Event,
    modifier: Modifier = Modifier
) = LazyColumn(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    item {
        val imageUrl = if (event.icon?.startsWith("http") == true) {
            event.icon
        } else {
            "https://appsterdam.rs/api/getImage.php?eid=${event.id}&for=${Uri.encode(event.name ?: "")}"
        }

        GlideImage(
            imageModel = imageUrl,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }

    item {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            // Date and Time
            Text(
                text = "\uD83D\uDCC5 ${DateUtils.formatEventDate(event.date)}",
                style = Typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Attendees
            Text(
                text = "\uD83E\uDEC2 ${event.attendees} attendees",
                style = Typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Location
            val context = LocalContext.current
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val gmmIntentUri = if ((event.latitude != null) && (event.longitude != null)) {
                            ("geo:${event.latitude},${event.longitude}?q=${Uri.encode(event.location_address ?: event.location_name)}").toUri()
                        } else {
                            ("geo:0,0?q=${Uri.encode(event.location_address ?: event.location_name)}").toUri()
                        }
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        @SuppressLint("QueryPermissionsNeeded")
                        if (mapIntent.resolveActivity(context.packageManager) != null) {
                            context.startActivity(mapIntent)
                        } else {
                            context.startActivity(Intent(Intent.ACTION_VIEW, gmmIntentUri))
                        }
                    }
                    .background(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Rounded.LocationOn,
                    contentDescription = "Location",
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = event.location_name ?: "Location",
                        style = Typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    event.location_address?.let {
                        Text(
                            text = it,
                            style = Typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Description
            Text(
                text = "Description",
                style = Typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            BioMarkdown(event.description ?: "No description provided.")
            
            Spacer(modifier = Modifier.height(32.dp))

        }
    }
}

@Composable
fun BioMarkdown(bio: String) = Box(
    modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp))
        .padding(16.dp)
) {
    MarkdownText(
        markdown = bio.replace("\n", "\n\n"),
        style = LocalTextStyle.current.copy(
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier.fillMaxWidth()
    )
}
