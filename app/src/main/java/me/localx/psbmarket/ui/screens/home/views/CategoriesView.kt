package me.localx.psbmarket.ui.screens.home.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.localx.icons.rounded.Icons
import me.localx.icons.rounded.outline.ArrowSmallRight
import me.localx.psbmarket.R
import me.localx.psbmarket.ui.screens.home.HomeScreenModel
import psbmarket.uikit.theme.Theme

@Composable
fun CategoriesView(screenModel: HomeScreenModel) {
    val categories by screenModel.categories.collectAsState()

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = {},
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = Theme.colors.text)
                )
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.screen_home_mostPopularCategories),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )

            Box(modifier = Modifier.width(16.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(36.dp)
                    .clip(CircleShape)
                    .background(Theme.colors.card)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.screen_home_all),
                    fontSize = 16.sp
                )
            }
        }

        if (categories != null) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                categories!!.take(3).forEach { item ->
                    Category(item.title)
                }
            }
        }
    }
}

@Composable
private fun Category(title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(Theme.shapes.small)
            .clickable(
                onClick = {},
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = Theme.colors.text)
            )
            .background(Theme.colors.card)
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Box(modifier = Modifier.width(16.dp))

        Icon(
            painter = rememberVectorPainter(Icons.Outline.ArrowSmallRight),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Theme.colors.textSecondary
        )
    }
}