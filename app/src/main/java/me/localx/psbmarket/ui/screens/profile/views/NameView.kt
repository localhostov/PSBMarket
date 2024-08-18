package me.localx.psbmarket.ui.screens.profile.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.localx.psbmarket.ui.screens.profile.ProfileScreenModel
import psbmarket.core.utils.formatPhoneNumber
import psbmarket.uikit.theme.Theme

@Composable
fun NameView(screenModel: ProfileScreenModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "${screenModel.user?.firstName} ${screenModel.user?.lastName}",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = "${screenModel.user?.email}",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            color = Theme.colors.textSecondary
        )

        Text(
            text = formatPhoneNumber("7${screenModel.user?.phone}"),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            color = Theme.colors.textSecondary
        )
    }
}