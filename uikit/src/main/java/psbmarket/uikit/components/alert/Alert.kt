package psbmarket.uikit.components.alert

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.window.Dialog
import psbmarket.uikit.theme.Theme

@Composable
public fun Alert(
    modifier: Modifier = Modifier,
    state: AlertState,
    title: String,
    caption: String,
    onDismiss: () -> Unit = state::hide,
    icon: (@Composable () -> Unit)? = null,
    onPositiveButtonText: String? = null,
    onNegativeButtonText: String? = null,
    onPositiveButtonClick: () -> Unit = onDismiss,
    onNegativeButtonClick: () -> Unit = onDismiss,
    autoHideOnButtonPressed: Boolean = true,
    reversedButtons: Boolean = false,
) {
    if (state.isVisible) {
        Dialog(onDismissRequest = onDismiss) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier
                    .clip(Theme.shapes.medium)
                    .background(Theme.colors.alertBackground)
                    .padding(16.dp),
            ) {
                icon?.invoke()

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Theme.colors.text,
                    )
                    Text(
                        text = caption,
                        fontSize = 16.sp,
                        color = Theme.colors.textSecondary,
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.End),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf("positive", "negative")
                        .let { if (reversedButtons) it.reversed() else it }
                        .fastMap { btn ->
                            if (onPositiveButtonText != null && btn == "positive") {
                                Text(
                                    text = onPositiveButtonText,
                                    fontSize = 16.sp,
                                    color = Theme.colors.alertPositiveButton,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.clip(CircleShape).clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = rememberRipple(color = Theme.colors.alertPositiveButton),
                                        onClick = {
                                            onPositiveButtonClick()
                                            if (autoHideOnButtonPressed) {
                                                state.hide()
                                            }
                                        }
                                    )
                                )
                            }

                            if (onNegativeButtonText != null && btn == "negative") {
                                Text(
                                    text = onNegativeButtonText,
                                    fontSize = 16.sp,
                                    color = Theme.colors.alertNegativeButton,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.clip(CircleShape).clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = rememberRipple(color = Theme.colors.alertNegativeButton),
                                        onClick = {
                                            onNegativeButtonClick()
                                            if (autoHideOnButtonPressed) {
                                                state.hide()
                                            }
                                        }
                                    )
                                )
                            }
                        }
                }
            }
        }
    }
}