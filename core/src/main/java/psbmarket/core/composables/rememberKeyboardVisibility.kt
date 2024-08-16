package psbmarket.core.composables

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView

public enum class KeyboardVisibility {
    Opened, Closed
}

@Composable
public fun rememberKeyboardVisibility(): State<KeyboardVisibility> {
    val view = LocalView.current
    val keyboardState = remember { mutableStateOf(KeyboardVisibility.Closed) }

    DisposableEffect(view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()

            view.getWindowVisibleDisplayFrame(rect)

            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - rect.bottom

            keyboardState.value = if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height
                KeyboardVisibility.Opened
            } else {
                KeyboardVisibility.Closed
            }
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)

        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }

    return keyboardState
}