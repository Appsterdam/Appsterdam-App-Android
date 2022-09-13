package rs.appsterdam.app.ui.screens.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import rs.appsterdam.app.models.Member

@Composable
fun MemberDescriptionSheet(member: Member, onClose: () -> Unit) = Column(
    modifier = Modifier
        .fillMaxWidth()
) {
    SheetHeader(onClose)
    SheetContent(member)
}


@Composable
fun SheetHeader(onClose: () -> Unit) = Box(
    modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(topStartPercent = 25, topEndPercent = 25))
        .background(color = Color.White),
    contentAlignment = Alignment.TopEnd,
) {
    IconButton(onClick = onClose) {
        Icon(imageVector = Icons.Rounded.Close, contentDescription = "Close member description")
    }
}

@Composable
fun SheetContent(member: Member) = Box(
    modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.White),
    contentAlignment = Alignment.TopCenter,
) {
    Text(member.name ?: "")
    Spacer(modifier = Modifier.height(80.dp))
}