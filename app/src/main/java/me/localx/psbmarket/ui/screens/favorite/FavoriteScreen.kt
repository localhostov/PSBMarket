package me.localx.psbmarket.ui.screens.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.rememberBalloonBuilder
import me.localx.icons.rounded.Icons
import me.localx.icons.rounded.outline.SettingsSliders
import me.localx.psbmarket.R
import me.localx.psbmarket.ui.screens.favorite.views.FavoriteCardView
import me.localx.psbmarket.ui.screens.favorite.views.FilterBalloonView
import psbmarket.uikit.components.toolbar.Toolbar
import psbmarket.uikit.components.toolbar.rememberToolbarScrollBehavior
import psbmarket.uikit.theme.Theme

class FavoriteScreen : Screen {
    @Composable
    override fun Content() {
        val scrollBehavior = rememberToolbarScrollBehavior()
        val screenModel: FavoriteScreenModel = getScreenModel()
        val favorites = screenModel.favorites.collectAsLazyPagingItems()
        val filterBalloonBuilder = rememberBalloonBuilder {
            isVisibleArrow = false
            setWidth(BalloonSizeSpec.WRAP)
            setHeight(BalloonSizeSpec.WRAP)
            setCornerRadius(16f)
            setMargin(16)
            setBalloonAnimation(BalloonAnimation.FADE)
        }.setBackgroundColor(Theme.colors.balloonBackground.toArgb())

        Scaffold(
            containerColor = Theme.colors.background,
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                Toolbar(
                    title = stringResource(R.string.screen_favorite_title),
                    scrollBehavior = scrollBehavior,
                    navigationIcon = {},
                    actions = {
                        Balloon(
                            builder = filterBalloonBuilder,
                            balloonContent = { FilterBalloonView(screenModel) }
                        ) { balloon ->
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .clickable(
                                        onClick = { balloon.showAlignBottom() },
                                        indication = rememberRipple(color = Theme.colors.text),
                                        interactionSource = remember { MutableInteractionSource() }
                                    )
                            ) {
                                Icon(
                                    painter = rememberVectorPainter(Icons.Outline.SettingsSliders),
                                    contentDescription = null,
                                    tint = Theme.colors.text,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                )
            },
        ) { contentPadding ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = contentPadding.calculateTopPadding())
            ) {
                items(favorites.itemCount) {
                    val item = favorites[it]

                    if (item != null) {
                        key(item.id) {
                            FavoriteCardView(item)
                        }
                    }
                }
            }
        }
    }
}