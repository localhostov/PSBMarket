package me.localx.psbmarket.ui.screens.home.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.localx.psbmarket.R
import me.localx.psbmarket.ui.views.ProductCardData
import me.localx.psbmarket.ui.views.ProductCardView
import psbmarket.graphql.GetMainPageOffersQuery
import psbmarket.uikit.theme.Theme

fun LazyListScope.offersView(offers: List<GetMainPageOffersQuery.Offer>?) {
    if (offers != null) {
        items(offers, key = { it.id }) { offer ->
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.headerModifier
                ) {
                    Text(
                        text = offer.title,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f)
                    )

                    Box(modifier = Modifier.width(16.dp))

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.allButton
                    ) {
                        Text(
                            text = stringResource(R.string.screen_home_all),
                            fontSize = 16.sp
                        )
                    }
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(offer.productsPage.content, key = { it.id }) { product ->
                        ProductCardView(
                            onChangeFavorite = {},
                            onAddToCart = {},
                            width = 200.dp,
                            item = ProductCardData(
                                title = product.title,
                                minFullPrice = product.minFullPrice,
                                minSellPrice = product.minSellPrice,
                                discountPercent = product.skuGroups.last().discountPercent,
                                feedbackQuantity = product.feedbackQuantity,
                                rating = product.rating,
                                image = product.photos[0].original.low,
                                favorite = product.favorite
                            )
                        )
                    }
                }
            }
        }
    }
}

private val Modifier.headerModifier
    @Composable
    get() = this
        .fillMaxWidth()
        .clickable(
            onClick = {},
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(color = Theme.colors.text)
        )
        .padding(vertical = 8.dp, horizontal = 16.dp)

private val Modifier.allButton
    @Composable
    get() = this
        .height(36.dp)
        .clip(CircleShape)
        .background(Theme.colors.card)
        .padding(horizontal = 16.dp)