package psbmarket.uikit.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

public data class BaseShapes(
    val tiny: CornerBasedShape,
    val extraSmall: CornerBasedShape,
    val small: CornerBasedShape,
    val medium: CornerBasedShape,
    val large: CornerBasedShape,
    val extraLarge: CornerBasedShape,
    val huge: CornerBasedShape,
)

public val defaultShapes: BaseShapes = BaseShapes(
    tiny = RoundedCornerShape(2.dp),
    extraSmall = RoundedCornerShape(6.dp),
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(14.dp),
    large = RoundedCornerShape(18.dp),
    extraLarge = RoundedCornerShape(22.dp),
    huge = RoundedCornerShape(28.dp),
)