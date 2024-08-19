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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import me.localx.psbmarket.R
import me.localx.psbmarket.ui.screens.home.HomeScreenModel
import me.localx.psbmarket.ui.views.ProductCardData
import me.localx.psbmarket.ui.views.ProductCardView
import psbmarket.uikit.theme.Theme

@Composable
fun OffersView(screenModel: HomeScreenModel) {
    val offers by screenModel.offers.collectAsState()
    val productCardWidth = LocalConfiguration.current.screenWidthDp / 2

    if (offers != null) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            offers!!.fastForEach { offer ->
                key(offer.id) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    onClick = {},
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(color = Theme.colors.text)
                                )
                                .padding(vertical = 8.dp, horizontal = 16.dp)
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
                                modifier = Modifier
                                    .height(36.dp)
                                    .clip(CircleShape)
                                    .background(Theme.colors.card)
                                    .padding(horizontal = 16.dp)
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
                            items(offer.productsPage.content) { product ->
                                ProductCardView(
                                    width = productCardWidth.dp,
                                    item = ProductCardData(
                                        title = product.title,
                                        minFullPrice = product.minFullPrice,
                                        minSellPrice = product.minSellPrice,
                                        discountPercent = product.skuGroups.last().discountPercent,
                                        feedbackQuantity = product.feedbackQuantity,
                                        rating = product.rating,
                                        image = product.photos[0].original.low
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}