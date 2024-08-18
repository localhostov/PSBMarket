package psbmarket.uikit.assets.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import me.localx.icons.rounded.Icons

public val Icons.Outline.Back: ImageVector
    get() {
        if (_back != null) {
            return _back!!
        }
        _back = ImageVector.Builder(
            name = "Back", defaultWidth = 23.0.dp, defaultHeight = 18.0.dp,
            viewportWidth = 23.0f, viewportHeight = 18.0f
        ).apply {
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveTo(21.85f, 7.8752f)
                horizontalLineTo(3.9263f)
                lineTo(10.0133f, 1.9205f)
                curveTo(10.4624f, 1.4812f, 10.4621f, 0.7691f, 10.0133f, 0.3295f)
                curveTo(9.5642f, -0.1098f, 8.8363f, -0.1098f, 8.3869f, 0.3295f)
                lineTo(0.3368f, 8.2045f)
                curveTo(-0.1122f, 8.6439f, -0.1122f, 9.3563f, 0.3366f, 9.7953f)
                lineTo(8.3866f, 17.6704f)
                curveTo(8.6111f, 17.89f, 8.9055f, 18.0f, 9.1999f, 18.0f)
                curveTo(9.4941f, 18.0f, 9.7885f, 17.89f, 10.0133f, 17.6701f)
                curveTo(10.4624f, 17.2311f, 10.4621f, 16.5186f, 10.0133f, 16.0796f)
                lineTo(3.926f, 10.1252f)
                horizontalLineTo(21.85f)
                curveTo(22.4851f, 10.1252f, 23.0003f, 9.6212f, 23.0f, 9.0002f)
                curveTo(23.0f, 8.3789f, 22.4851f, 7.8752f, 21.85f, 7.8752f)
                close()
            }
        }
            .build()
        return _back!!
    }

private var _back: ImageVector? = null