package me.localx.psbmarket.ui.screens.overview.views

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.localx.icons.rounded.Icons
import me.localx.icons.rounded.outline.ArrowSmallRight
import me.localx.icons.rounded.outline.MinusSmall
import me.localx.icons.rounded.outline.PlusSmall
import psbmarket.uikit.theme.Theme

@Composable
fun CategoryAccordion(
    text: String,
    level: Int,
    onClick: () -> Unit,
    content: (@Composable () -> Unit)? = null
) {
    var isOpen by rememberSaveable { mutableStateOf(false) }

    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
        ) {
            if (level > 0) {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    (1..level).forEach { _ ->
                        Box(modifier = Modifier
                            .fillMaxHeight()
                            .width(2.dp)
                            .background(Theme.colors.card)
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .weight(1f)
                    .height(48.dp)
                    .clip(Theme.shapes.small)
                    .clickable(
                        onClick = onClick,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(color = Theme.colors.text)
                    )
                    .background(Theme.colors.card)
                    .padding(start = 16.dp)
                    .padding(2.dp)
            ) {
                Text(
                    text = text,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Box(modifier = Modifier.width(16.dp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(Theme.shapes.small)
                        .background(if (content == null) Theme.colors.cardSecondary else Theme.colors.accent)
                        .clickable(
                            onClick = {
                                if (content == null) {
                                    onClick()
                                } else {
                                    isOpen = !isOpen
                                }
                            },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(color = Theme.colors.text)
                        )
                ) {
                    if (content == null) {
                        Icon(
                            painter = rememberVectorPainter(Icons.Outline.ArrowSmallRight),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Theme.colors.text
                        )
                    } else {
                        AnimatedContent(
                            targetState = isOpen,
                            label = "action icon",
                            contentAlignment = Alignment.Center,
                            transitionSpec = {
                                fadeIn() + scaleIn() togetherWith fadeOut() + scaleOut()
                            }
                        ) { isOpen ->
                            Icon(
                                painter = rememberVectorPainter(if (isOpen) Icons.Outline.MinusSmall else Icons.Outline.PlusSmall),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = Theme.colors.text
                            )
                        }
                    }
                }
            }
        }

        if (content != null) {
            AnimatedVisibility(visible = isOpen) {
                Box(modifier = Modifier.height(4.dp))
                content()
            }
        }
    }
}