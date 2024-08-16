package psbmarket.uikit.theme

import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.TextStyle

public object Theme {
    public val colors: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorScheme.current
}

@Composable
public fun AppTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalColorScheme provides defaultColorScheme
    ) {
        ProvideTextStyle(
            value = TextStyle.Default.copy(color = Theme.colors.text),
            content = content
        )
    }
}