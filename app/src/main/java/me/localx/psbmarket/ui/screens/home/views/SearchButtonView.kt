package me.localx.psbmarket.ui.screens.home.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.localx.icons.rounded.Icons
import me.localx.icons.rounded.outline.Search
import me.localx.psbmarket.R
import psbmarket.uikit.theme.Theme

@Composable
fun SearchButtonView() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(top = 16.dp)
            .statusBarsPadding()
            .height(60.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(Theme.shapes.medium)
            .background(lerp(Theme.colors.accent, Color.Black, .6f))
            .clickable(
                indication = rememberRipple(color = Theme.colors.accent),
                interactionSource = remember { MutableInteractionSource() },
                onClick = {}
            )
            .padding(horizontal = 16.dp)
    ) {
        Icon(
            painter = rememberVectorPainter(Icons.Outline.Search),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Theme.colors.accent,
        )

        Text(
            text = stringResource(R.string.screen_home_search),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Theme.colors.accent
        )
    }
}