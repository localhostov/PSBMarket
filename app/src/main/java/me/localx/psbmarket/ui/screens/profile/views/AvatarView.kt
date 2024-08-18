package me.localx.psbmarket.ui.screens.profile.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.localx.psbmarket.ui.screens.profile.ProfileScreenModel
import psbmarket.uikit.theme.Theme

@Composable
fun AvatarView(screenModel: ProfileScreenModel) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .background(Theme.colors.accent.copy(alpha = 0.5f))
            .border(2.dp, Theme.colors.accent, CircleShape)
    ) {
        Text(
            text = "${screenModel.user?.firstName?.first()}${screenModel.user?.lastName?.first()}",
            fontWeight = FontWeight.SemiBold,
            fontSize = 48.sp,
            color = Theme.colors.accent
        )
    }
}