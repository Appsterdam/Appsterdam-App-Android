package rs.appsterdam.app.models

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter // Import this
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProfileImage(member: Member) {
    val painter = rememberAsyncImagePainter(
        model = member.picture,
        placeholder = rememberVectorPainter(image = Icons.Rounded.Person), // Use rememberVectorPainter
        error = rememberVectorPainter(image = Icons.Rounded.Person)       // Use rememberVectorPainter
    )

    if (painter.state is AsyncImagePainter.State.Error) {
        Icon(
            imageVector = Icons.Rounded.Person,
            contentDescription = "Profile Error",
            modifier = Modifier
                .size(140.dp)
                .padding(10.dp)
                .clip(CircleShape)
        )
    } else {
        AsyncImage(
            model = member.picture,
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(140.dp)
                .padding(10.dp)
                .clip(CircleShape)
        )
    }
}