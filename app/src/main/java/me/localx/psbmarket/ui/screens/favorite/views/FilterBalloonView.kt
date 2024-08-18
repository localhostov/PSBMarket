package me.localx.psbmarket.ui.screens.favorite.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.localx.icons.rounded.Icons
import me.localx.icons.rounded.outline.Check
import me.localx.psbmarket.R
import me.localx.psbmarket.ui.screens.favorite.FavoriteScreenModel
import psbmarket.graphql.type.FavoritesSorting
import psbmarket.graphql.type.SortingOrder
import psbmarket.uikit.theme.Theme

@Composable
fun FilterBalloonView(screenModel: FavoriteScreenModel) {
    val state by screenModel.state.collectAsState()

    Column {
        Option(
            text = stringResource(R.string.screen_favorite_filterAscendingPrice),
            selected = state.order == SortingOrder.ASCENDING && state.sortBy == FavoritesSorting.PRICE,
            onClick = {
                screenModel.updateFilters(
                    sortBy = FavoritesSorting.PRICE,
                    order = SortingOrder.ASCENDING
                )
            }
        )
        Option(
            text = stringResource(R.string.screen_favorite_filterDescendingPrice),
            selected = state.order == SortingOrder.DESCENDING && state.sortBy == FavoritesSorting.PRICE,
            onClick = {
                screenModel.updateFilters(
                    sortBy = FavoritesSorting.PRICE,
                    order = SortingOrder.DESCENDING
                )
            }
        )
        Option(
            text = stringResource(R.string.screen_favorite_filterDescendingDate),
            selected = state.order == SortingOrder.DESCENDING && state.sortBy == FavoritesSorting.DATE_ADDED,
            onClick = {
                screenModel.updateFilters(
                    sortBy = FavoritesSorting.DATE_ADDED,
                    order = SortingOrder.DESCENDING
                )
            }
        )
    }
}

@Composable
private fun Option(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = rememberRipple(color = Theme.colors.text),
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
            .padding(vertical = 16.dp, horizontal = 20.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
        )

        if (selected) {
            Box(modifier = Modifier.width(16.dp))
            Icon(
                painter = rememberVectorPainter(Icons.Outline.Check),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Theme.colors.accent
            )
        }
    }
}