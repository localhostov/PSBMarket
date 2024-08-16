package psbmarket.uikit.theme

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

public val LocalColorScheme: ProvidableCompositionLocal<ColorScheme> = staticCompositionLocalOf {
    noLocalProvidedFor("LocalColorScheme")
}

public val LocalShapes: ProvidableCompositionLocal<BaseShapes> = staticCompositionLocalOf {
    noLocalProvidedFor("LocalShapes")
}

private fun noLocalProvidedFor(name: String): Nothing {
    error("No default $name provided")
}