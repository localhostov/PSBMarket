package me.localx.psbmarket.ui.screens.favorite

import androidx.compose.runtime.Immutable
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import psbmarket.core.Constants
import psbmarket.core.data.paging.FavoritesPagingSource
import psbmarket.graphql.GetFavoritesQuery
import psbmarket.graphql.type.FavoritesSorting
import psbmarket.graphql.type.SortingOrder
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@Immutable
class FavoriteScreenModel @Inject constructor(
    private val favoritesPagingSource: FavoritesPagingSource.Factory
) : ScreenModel {
    val state = MutableStateFlow(FavoriteScreenState())
    val favorites: StateFlow<PagingData<GetFavoritesQuery.SkuGroupFavorite>> = state
        .map { createPager(it.order, it.sortBy) }
        .flatMapLatest { it.flow }
        .cachedIn(screenModelScope)
        .stateIn(screenModelScope, SharingStarted.Lazily, PagingData.empty())

    fun updateFilters(sortBy: FavoritesSorting, order: SortingOrder) {
        screenModelScope.launch {
            state.update {
                it.copy(
                    sortBy = sortBy,
                    order = order
                )
            }
        }
    }

    private fun createPager(
        order: SortingOrder,
        sortBy: FavoritesSorting,
    ): Pager<Int, GetFavoritesQuery.SkuGroupFavorite> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.DEFAULT_PAGING_PAGE_SIZE,
                prefetchDistance = Constants.DEFAULT_PAGING_PAGE_SIZE / 2
            ),
            pagingSourceFactory = {
                favoritesPagingSource.create(
                    order = order,
                    sortBy = sortBy
                )
            }
        )
    }
}