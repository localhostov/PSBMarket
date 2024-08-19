package psbmarket.uikit.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

public object Theme {
    public val colors: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorScheme.current

    public val shapes: BaseShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current
}

public val defaultTextFieldColors: TextFieldColors
    @Composable
    get() = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        cursorColor = Theme.colors.accent,
        unfocusedContainerColor = Theme.colors.inputBackground,
        focusedContainerColor = Theme.colors.inputBackground,
        focusedPlaceholderColor = Theme.colors.inputPlaceholder,
        unfocusedPlaceholderColor = Theme.colors.inputPlaceholder,
    )

@Composable
public fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        CompositionLocalProvider(
            LocalColorScheme provides defaultColorScheme,
            LocalShapes provides defaultShapes
        ) {
            ProvideTextStyle(
                value = TextStyle.Default.copy(color = Theme.colors.text),
                content = content
            )
        }
    }
}