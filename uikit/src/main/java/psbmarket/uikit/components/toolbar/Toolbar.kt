package psbmarket.uikit.components.toolbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import me.localx.icons.rounded.Icons
import psbmarket.uikit.assets.icons.Back
import psbmarket.uikit.theme.Theme
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
public fun DefaultNavigationIcon(onBack: (() -> Unit)? = null) {
    val navigator = LocalNavigator.currentOrThrow
    val keyboardController = LocalSoftwareKeyboardController.current

    fun back() {
        onBack?.invoke() ?: run {
            keyboardController?.hide()
            navigator.pop()
        }
    }

    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .clickable { back() },
        contentAlignment = Alignment.CenterStart
    ) {
        Icon(
            painter = rememberVectorPainter(Icons.Outline.Back),
            contentDescription = null,
            tint = Theme.colors.text,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
public fun Toolbar(
    modifier: Modifier = Modifier,
    navigationIcon: (@Composable () -> Unit)? = { DefaultNavigationIcon() },
    actions: (@Composable RowScope.() -> Unit)? = null,
    centralContent: (@Composable () -> Unit)? = null,
    additionalContent: (@Composable () -> Unit)? = null,
    title: String? = null,
    collapsedStyle: TextStyle = MaterialTheme.typography.headlineMedium.copy(
        fontWeight = FontWeight(700),
        color = Theme.colors.text
    ),
    expandedTitleStyle: TextStyle = MaterialTheme.typography.headlineMedium.copy(
        fontWeight = FontWeight(700),
        color = Theme.colors.text
    ),
    scrollBehavior: ToolbarScrollBehavior,
) {
    val collapsedFraction by remember {
        derivedStateOf {
            if (centralContent == null) scrollBehavior.state.collapsedFraction else 0f
        }
    }

    val fullyCollapsedTitleScale = when {
        !title.isNullOrEmpty() -> COLLAPSED_TITLE_LINE_HEIGHT.value / collapsedStyle.lineHeight.value
        else -> 1f
    }

    val collapsingTitleScale by remember {
        derivedStateOf {
            lerp(1f, fullyCollapsedTitleScale, collapsedFraction)
        }
    }

    Surface(
        modifier = modifier,
        color = Theme.colors.background
    ) {
        Layout(
            modifier = modifier.then(
                Modifier
                    .heightIn(min = MIN_COLLAPSED_HEIGHT)
                    .statusBarsPadding()
            ),
            content = {
                if (!title.isNullOrEmpty()) {
                    Text(
                        modifier = Modifier
                            .layoutId(EXPANDED_TITLE_ID)
                            .wrapContentHeight(align = Alignment.Top)
                            .graphicsLayer {
                                scaleX = collapsingTitleScale
                                scaleY = collapsingTitleScale
                                transformOrigin = TransformOrigin(0f, 0f)
                            },
                        text = title,
                        style = expandedTitleStyle
                    )

                    Text(
                        modifier = Modifier
                            .layoutId(COLLAPSED_TITLE_ID)
                            .wrapContentHeight(align = Alignment.Top)
                            .graphicsLayer {
                                scaleX = collapsingTitleScale
                                scaleY = collapsingTitleScale
                                transformOrigin = TransformOrigin(0f, 0f)
                            },
                        text = title,
                        style = collapsedStyle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                if (navigationIcon != null) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .layoutId(NAVIGATION_ICON_ID)
                    ) {
                        navigationIcon()
                    }
                }

                if (actions != null) {
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .layoutId(ACTIONS_ID)
                    ) {
                        actions()
                    }
                }

                if (centralContent != null) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .layoutId(CENTRAL_CONTENT_ID)
                    ) {
                        centralContent()
                    }
                }

                if (additionalContent != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .layoutId(ADDITIONAL_CONTENT_ID)
                    ) {
                        additionalContent()
                    }
                }
            },
        ) { measurables, constraints ->
            val horizontalPaddingPx = HORIZONTAL_PADDING.toPx()
            val expandedTitleBottomPaddingPx = EXPANDED_TITLE_BOTTOM_PADDING.toPx()

            val navigationIconPlaceable = measurables.firstOrNull { it.layoutId == NAVIGATION_ICON_ID }
                ?.measure(constraints.copy(minWidth = 0))

            val actionsPlaceable = measurables.firstOrNull { it.layoutId == ACTIONS_ID }
                ?.measure(constraints.copy(minWidth = 0))

            val expandedTitlePlaceable = measurables.firstOrNull { it.layoutId == EXPANDED_TITLE_ID }
                ?.measure(
                    constraints.copy(
                        maxWidth = (constraints.maxWidth - 2 * horizontalPaddingPx).roundToInt(),
                        minWidth = 0,
                        minHeight = 0
                    )
                )

            val additionalContentPlaceable = measurables.firstOrNull { it.layoutId == ADDITIONAL_CONTENT_ID }
                ?.measure(constraints)

            val navigationIconOffset = if (navigationIconPlaceable == null) {
                horizontalPaddingPx
            } else {
                navigationIconPlaceable.width + horizontalPaddingPx * 2
            }

            val actionsOffset = if (actionsPlaceable == null) {
                horizontalPaddingPx
            } else {
                actionsPlaceable.width + horizontalPaddingPx * 2
            }

            val collapsedTitleMaxWidthPx =
                (constraints.maxWidth - navigationIconOffset - actionsOffset) / fullyCollapsedTitleScale

            val collapsedTitlePlaceable = measurables.firstOrNull { it.layoutId == COLLAPSED_TITLE_ID }
                ?.measure(
                    constraints.copy(
                        maxWidth = collapsedTitleMaxWidthPx.roundToInt(),
                        minWidth = 0,
                        minHeight = 0
                    )
                )

            val centralContentPlaceable = measurables.firstOrNull { it.layoutId == CENTRAL_CONTENT_ID }
                ?.measure(
                    constraints.copy(
                        minWidth = 0,
                        maxWidth = (constraints.maxWidth - navigationIconOffset - actionsOffset).roundToInt()
                    )
                )

            val collapsedHeightPx = when {
                centralContentPlaceable != null ->
                    max(MIN_COLLAPSED_HEIGHT.toPx(), centralContentPlaceable.height.toFloat())
                else -> MIN_COLLAPSED_HEIGHT.toPx()
            }

            var layoutHeightPx = collapsedHeightPx

            val navigationIconX = horizontalPaddingPx.roundToInt()
            val navigationIconY = ((collapsedHeightPx - (navigationIconPlaceable?.height ?: 0)) / 2).roundToInt()

            val actionsX = (constraints.maxWidth - (actionsPlaceable?.width ?: 0) - horizontalPaddingPx).roundToInt()
            val actionsY = ((collapsedHeightPx - (actionsPlaceable?.height ?: 0)) / 2).roundToInt()

            var collapsingTitleY = 0
            var collapsingTitleX = 0

            if (expandedTitlePlaceable != null && collapsedTitlePlaceable != null) {
                val heightOffsetLimitPx = expandedTitlePlaceable.height + expandedTitleBottomPaddingPx
                scrollBehavior.state.heightOffsetLimit = when (centralContent) {
                    null -> -heightOffsetLimitPx
                    else -> -1f
                }

                val fullyExpandedHeightPx = MIN_COLLAPSED_HEIGHT.toPx() + heightOffsetLimitPx
                val fullyExpandedTitleY = fullyExpandedHeightPx - expandedTitlePlaceable.height - expandedTitleBottomPaddingPx
                val fullyCollapsedTitleY = collapsedHeightPx / 2 - COLLAPSED_TITLE_LINE_HEIGHT.toPx().roundToInt() / 2

                layoutHeightPx = lerp(fullyExpandedHeightPx, collapsedHeightPx, collapsedFraction)

                collapsingTitleX = lerp(horizontalPaddingPx, navigationIconOffset, collapsedFraction).roundToInt()
                collapsingTitleY = lerp(fullyExpandedTitleY, fullyCollapsedTitleY, collapsedFraction).roundToInt()
            } else {
                scrollBehavior.state.heightOffsetLimit = -1f
            }

            val toolbarHeightPx = layoutHeightPx.roundToInt() + (additionalContentPlaceable?.height ?: 0)

            layout(constraints.maxWidth, toolbarHeightPx) {
                navigationIconPlaceable?.placeRelative(
                    x = navigationIconX,
                    y = navigationIconY
                )
                actionsPlaceable?.placeRelative(
                    x = actionsX,
                    y = actionsY
                )
                centralContentPlaceable?.placeRelative(
                    x = navigationIconOffset.roundToInt(),
                    y = ((collapsedHeightPx - centralContentPlaceable.height) / 2).roundToInt()
                )
                if (expandedTitlePlaceable?.width == collapsedTitlePlaceable?.width) {
                    expandedTitlePlaceable?.placeRelative(
                        x = collapsingTitleX,
                        y = collapsingTitleY,
                    )
                } else {
                    expandedTitlePlaceable?.placeRelativeWithLayer(
                        x = collapsingTitleX,
                        y = collapsingTitleY,
                        layerBlock = { alpha = 1 - collapsedFraction }
                    )
                    collapsedTitlePlaceable?.placeRelativeWithLayer(
                        x = collapsingTitleX,
                        y = collapsingTitleY,
                        layerBlock = { alpha = collapsedFraction }
                    )
                }
                additionalContentPlaceable?.placeRelative(
                    x = 0,
                    y = layoutHeightPx.roundToInt()
                )
            }
        }
    }
}

private fun lerp(a: Float, b: Float, fraction: Float): Float {
    return a + fraction * (b - a)
}

private val MIN_COLLAPSED_HEIGHT = 56.dp
private val HORIZONTAL_PADDING = 16.dp
private val EXPANDED_TITLE_BOTTOM_PADDING = 8.dp
private val COLLAPSED_TITLE_LINE_HEIGHT = 28.sp

private const val EXPANDED_TITLE_ID = "EXPANDED_TITLE"
private const val COLLAPSED_TITLE_ID = "COLLAPSED_TITLE"
private const val NAVIGATION_ICON_ID = "NAVIGATION_ICON"
private const val ACTIONS_ID = "ACTIONS"
private const val CENTRAL_CONTENT_ID = "CENTRAL_CONTENT"
private const val ADDITIONAL_CONTENT_ID = "ADDITIONAL_CONTENT"