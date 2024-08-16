package psbmarket.uikit.theme

import androidx.compose.ui.graphics.Color

public data class ColorScheme(
    val accent: Color,
    val text: Color,
)

public val defaultColorScheme: ColorScheme = ColorScheme(
    accent = Color(0xFF4748D9),
    text = Color(0xFFFFFFFF)
)