package me.localx.psbmarket.ui.screens.home.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import me.localx.psbmarket.ui.screens.home.HomeScreenModel
import psbmarket.uikit.theme.Theme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannersView(screenModel: HomeScreenModel) {
    val banners by screenModel.banners.collectAsState()
    val bannersState = rememberLazyListState()
    val bannersFlingBehavior = rememberSnapFlingBehavior(bannersState)

    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            bannersState.scroll(MutatePriority.UserInput) {
                val velocity = if (bannersState.canScrollForward) 7_000f else -100_000f

                with(bannersFlingBehavior) {
                    performFling(velocity)
                }
            }
        }
    }

    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
        LazyRow(
            state = bannersState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            flingBehavior = bannersFlingBehavior,
        ) {
            if (banners != null) {
                items(banners!!, key = { it.id }) { item ->
                    AsyncImage(
                        model = item.image.low,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(width = 300.dp, height = 170.dp)
                            .clip(Theme.shapes.medium)
                            .background(Theme.colors.card)
                            .clickable(
                                onClick = {},
                                indication = rememberRipple(color = Color.White),
                                interactionSource = remember { MutableInteractionSource() }
                            )
                    )
                }
            }
        }
    }
}