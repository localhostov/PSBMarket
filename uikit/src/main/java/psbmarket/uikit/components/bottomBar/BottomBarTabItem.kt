package psbmarket.uikit.components.bottomBar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import psbmarket.uikit.theme.Theme

@Immutable
public data class Tab(
    val tab: cafe.adriel.voyager.navigator.tab.Tab,
    val inactiveIcon: ImageVector,
    val activeIcon: ImageVector,
)

@Composable
public fun RowScope.BottomBarTabItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    val dotSize by animateDpAsState(
        targetValue = if (tabNavigator.current == tab.tab) 8.dp else 0.dp,
        label = "indicator dot size",
        animationSpec = tween(350)
    )

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .weight(1f)
            .clip(Theme.shapes.medium)
            .clickable(
                indication = rememberRipple(color = Theme.colors.bottomNavigationIconActive),
                interactionSource = remember { MutableInteractionSource() },
                onClick = { tabNavigator.current = tab.tab },
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TabIcon(tab, tabNavigator.current == tab.tab)

        Box(modifier = Modifier.layout { measurable, _ ->
            val size = dotSize.toPx()
            val placeable = measurable.measure(Constraints.fixed(size.toInt(), size.toInt()))

            layout(placeable.width, placeable.height) {
                placeable.placeRelative(0, 0)
            }
        })

        Box(modifier = Modifier
            .clip(CircleShape)
            .background(Theme.colors.bottomNavigationDot)
            .layout { measurable, _ ->
                val size = dotSize.toPx()
                val placeable = measurable.measure(Constraints.fixed(size.toInt(), size.toInt()))

                layout(placeable.width, placeable.height) {
                    placeable.placeRelative(0, 0)
                }
            })
    }
}

@Composable
private fun TabIcon(tab: Tab, selected: Boolean) {
    val iconColor by animateColorAsState(
        targetValue = if (selected) {
            Theme.colors.bottomNavigationIconActive
        } else {
            Theme.colors.bottomNavigationIconInactive
        },
        label = "tab icon color",
        animationSpec = tween(350)
    )

    AnimatedContent(
        targetState = if (selected) tab.activeIcon else tab.inactiveIcon,
        label = "icon",
        transitionSpec = { fadeIn() togetherWith fadeOut() }
    ) { icon ->
        Icon(
            painter = rememberVectorPainter(icon),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithContent {
                    drawContent()

                    drawRect(
                        color = iconColor,
                        blendMode = BlendMode.SrcIn
                    )
                }
        )
    }
}