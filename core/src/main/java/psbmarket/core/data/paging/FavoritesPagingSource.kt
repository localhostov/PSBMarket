package psbmarket.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.apollographql.apollo.exception.NoDataException
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import psbmarket.core.Constants
import psbmarket.graphql.GetFavoritesQuery
import psbmarket.graphql.type.FavoritesFilter
import psbmarket.graphql.type.FavoritesSorting
import psbmarket.graphql.type.SortingOrder

public class FavoritesPagingSource @AssistedInject constructor(
    @Assisted private val order: SortingOrder,
    @Assisted private val sortBy: FavoritesSorting,
    private val apolloClient: ApolloClient
) : PagingSource<Int, GetFavoritesQuery.SkuGroupFavorite>() {
    @AssistedFactory
    public interface Factory {
        public fun create(
            order: SortingOrder,
            sortBy: FavoritesSorting
        ): FavoritesPagingSource
    }

    override fun getRefreshKey(state: PagingState<Int, GetFavoritesQuery.SkuGroupFavorite>): Int? {
        val position = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(position) ?: return null

        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetFavoritesQuery.SkuGroupFavorite> {
        val page = params.key ?: 0
        val pageSize = params.loadSize.coerceAtMost(Constants.DEFAULT_PAGING_PAGE_SIZE)

        val response = apolloClient.query(GetFavoritesQuery(
            filter = Optional.present(
                FavoritesFilter(
                    offset = Optional.present(page),
                    order = Optional.present(order),
                    page = Optional.present(page),
                    size = Optional.present(pageSize),
                    sortBy = Optional.present(sortBy)
                )
            )
        )).execute()

        if (response.hasErrors()) {
            return LoadResult.Error(Exception("no data"))
        } else {
            try {
                val data = response.dataOrThrow()

                val nextKey = if (data.skuGroupFavorites.size < pageSize) null else page + 1
                val prevKey = if (page == 1) null else page - 1

                return LoadResult.Page(
                    data = data.skuGroupFavorites.mapNotNull { it },
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } catch (e: NoDataException) {
                return LoadResult.Error(e)
            }
        }
    }
}