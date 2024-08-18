package me.localx.psbmarket.ui.screens.favorite

import psbmarket.graphql.type.FavoritesSorting
import psbmarket.graphql.type.SortingOrder

data class FavoriteScreenState(
    val order: SortingOrder = SortingOrder.DESCENDING,
    val sortBy: FavoritesSorting = FavoritesSorting.DATE_ADDED
)