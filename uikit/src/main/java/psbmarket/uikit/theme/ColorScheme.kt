package psbmarket.uikit.theme

import androidx.compose.ui.graphics.Color

public data class ColorScheme(
    val accent: Color,
    val text: Color,
    val background: Color,
    val bottomNavigationBackground: Color,
    val bottomNavigationDot: Color,
    val bottomNavigationIconActive: Color,
    val bottomNavigationIconInactive: Color,
)

public val defaultColorScheme: ColorScheme = ColorScheme(
    accent = Color(0xFF4748D9),
    text = Color(0xFFFFFFFF),
    background = Color(0xFF000000),
    bottomNavigationBackground = Color(0xFF141414),
    bottomNavigationDot = Color(0xFF4D4D4D),
    bottomNavigationIconActive = Color(0xFFFFFFFF),
    bottomNavigationIconInactive = Color(0xFF595959)
)