package psbmarket.uikit.theme

import androidx.compose.ui.graphics.Color

public data class ColorScheme(
    val accent: Color,
    val background: Color,

    val success: Color,
    val error: Color,
    val discount: Color,
    val price: Color,

    val text: Color,
    val textSecondary: Color,
    val divider: Color,

    val bottomNavigationBackground: Color,
    val bottomNavigationDot: Color,
    val bottomNavigationIconActive: Color,
    val bottomNavigationIconInactive: Color,

    val buttonPrimaryBackground: Color,
    val buttonPrimaryForeground: Color,

    val buttonSecondaryBackground: Color,
    val buttonSecondaryForeground: Color,

    val card: Color,
    val cardSecondary: Color,

    val inputBackground: Color,
    val inputPlaceholder: Color,

    val toasterBackground: Color,
    val toasterBorder: Color,

    val alertBackground: Color,
    val alertPositiveButton: Color,
    val alertNegativeButton: Color,

    val balloonBackground: Color,
)

public val defaultColorScheme: ColorScheme = ColorScheme(
    accent = Color(0xFF4748D9),
    background = Color(0xFF000000),

    success = Color(0xFF41AC0F),
    error = Color(0xFFAC0F0F),
    discount = Color(0xffdc3c3c),
    price = Color(0xff5b5cdc),

    text = Color(0xFFFFFFFF),
    textSecondary = Color(0xFF606060),
    divider = Color(0xFF2B2B2B),

    bottomNavigationBackground = Color(0xFF141414),
    bottomNavigationDot = Color(0xFF4D4D4D),
    bottomNavigationIconActive = Color(0xFFFFFFFF),
    bottomNavigationIconInactive = Color(0xFF595959),

    buttonPrimaryBackground = Color(0xFF2B2B2B),
    buttonPrimaryForeground = Color.White,

    buttonSecondaryBackground = Color(0xFFFFFFFF),
    buttonSecondaryForeground = Color.Black,

    card = Color(0xFF1C1C1C),
    cardSecondary = Color(0xFF333333),

    inputBackground = Color(0xFF121212),
    inputPlaceholder = Color(0xFF5C5C5C),

    toasterBackground = Color(0xFF262626),
    toasterBorder = Color(0xFF333333),

    alertBackground = Color(0xFF1F1F1F),
    alertPositiveButton = Color(0xFF4572E5),
    alertNegativeButton = Color(0xFFE54545),

    balloonBackground = Color(0xFF171717)
)