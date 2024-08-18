package psbmarket.uikit.components.alert

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
public fun rememberAlertState(): AlertState {
    return remember { AlertState() }
}

public class AlertState {
    public var isVisible: Boolean by mutableStateOf(false)
        private set

    public fun show() {
        isVisible = true
    }

    public fun hide() {
        isVisible = false
    }
}