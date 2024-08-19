package me.localx.psbmarket.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isUnspecified
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import me.localx.icons.rounded.Icons
import me.localx.icons.rounded.filled.Star
import me.localx.icons.rounded.outline.CrossSmall
import me.localx.psbmarket.R
import psbmarket.core.utils.declOfNum
import psbmarket.uikit.theme.Theme
import java.util.Locale

data class ProductCardData(
    val minSellPrice: Int,
    val minFullPrice: Int,
    val discountPercent: String?,
    val title: String,
    val feedbackQuantity: Int,
    val rating: Double,
    val image: String,
)

@Composable
fun ProductCardView(
    item: ProductCardData,
    width: Dp = Dp.Unspecified
) {
    Column(
        modifier = Modifier
            .then(if (width.isUnspecified) Modifier else Modifier.width(width))
            .clip(Theme.shapes.medium)
            .background(Theme.colors.card)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = Color.White),
                onClick = {}
            )
    ) {
        Image(
            url = item.image,
            discountPercent = item.discountPercent
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .height(160.dp)
                .height(IntrinsicSize.Max)
                .padding(8.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    append("${"%,d".format(locale = Locale("ru"), item.minSellPrice / 100)}₽")
                    if (item.discountPercent != null) {
                        append(" ")
                        withStyle(
                            SpanStyle(
                                color = Theme.colors.textSecondary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                textDecoration = TextDecoration.LineThrough
                            )
                        ) {
                            append("${"%,d".format(locale = Locale("ru"), item.minFullPrice / 100)}₽")
                        }
                    }
                },
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Theme.colors.price
            )

            Text(
                text = item.title,
                maxLines = 2,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (item.feedbackQuantity > 0) {
                    Icon(
                        painter = rememberVectorPainter(Icons.Filled.Star),
                        contentDescription = null,
                        tint = Theme.colors.accent,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Text(
                    text = buildAnnotatedString {
                        if (item.feedbackQuantity == 0) {
                            withStyle(SpanStyle(color = Theme.colors.textSecondary)) {
                                append(stringResource(R.string.screen_favorite_emptyFeedback))
                            }
                        } else {
                            append("%.1f".format(item.rating))
                            withStyle(SpanStyle(fontSize = 15.sp, color = Theme.colors.textSecondary)) {
                                val title = declOfNum(
                                    number = item.feedbackQuantity,
                                    titles = stringArrayResource(R.array.screen_favorite_rating),
                                    withNumber = false
                                )
                                append(" • ${"%,d".format(locale = Locale("ru"), item.feedbackQuantity)} $title")
                            }
                        }
                    },
                    maxLines = 1,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Box(modifier = Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .height(36.dp)
                    .clip(Theme.shapes.small)
                    .background(Theme.colors.buttonPrimaryBackground)
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(color = Theme.colors.buttonPrimaryForeground),
                        onClick = {}
                    )
            ) {
                Text(
                    text = stringResource(R.string.screen_favorite_addToCard),
                    color = Theme.colors.buttonPrimaryForeground,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
private fun Image(url: String, discountPercent: String?) {
    Box {
        AsyncImage(
            model = url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f)
                .background(Theme.colors.cardSecondary)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            if (discountPercent != null) {
                Box(
                    modifier = Modifier
                        .clip(Theme.shapes.extraSmall)
                        .background(lerp(Theme.colors.discount, Color.White, 0.75f))
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = discountPercent,
                        color = Theme.colors.discount,
                        fontSize = 14.sp
                    )
                }
            } else {
                Box(Modifier)
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            ) {
                Icon(
                    painter = rememberVectorPainter(Icons.Outline.CrossSmall),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}