package psbmarket.uikit.components.bottomBar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import psbmarket.core.composables.KeyboardVisibility
import psbmarket.core.composables.rememberKeyboardVisibility
import psbmarket.uikit.theme.Theme

@Composable
public fun BottomBar(content: @Composable RowScope.() -> Unit) {
    val keyboardVisibility by rememberKeyboardVisibility()
    val bottomBarInsetsPadding = WindowInsets.systemBars
        .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
        .asPaddingValues()

    AnimatedContent(
        targetState = keyboardVisibility,
        label = "bottom bar",
        transitionSpec = {
            (slideInVertically() + fadeIn() togetherWith slideOutVertically() + fadeOut()) using
                    SizeTransform(clip = false)
        },
    ) { visibility ->
        if (visibility == KeyboardVisibility.Closed) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Theme.colors.bottomNavigationBackground)
                    .padding(bottomBarInsetsPadding)
                    .height(BOTTOM_BAR_HEIGHT)
                    .fillMaxWidth(),
                content = content
            )
        }
    }
}

public val BOTTOM_BAR_HEIGHT: Dp = 64.dp