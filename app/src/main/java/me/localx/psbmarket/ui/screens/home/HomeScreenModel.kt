package me.localx.psbmarket.ui.screens.home

import android.content.Context
import androidx.compose.runtime.Immutable
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.exception.NoDataException
import com.dokar.sonner.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.localx.psbmarket.R
import psbmarket.core.data.event.AppEvent
import psbmarket.core.data.event.AppEventBus
import psbmarket.graphql.GetMainPageBannersQuery
import psbmarket.graphql.GetMainPageCategoriesQuery
import psbmarket.graphql.GetMainPageOffersQuery
import javax.inject.Inject

@Immutable
class HomeScreenModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apolloClient: ApolloClient,
    private val appEventBus: AppEventBus,
) : ScreenModel {
    val banners = MutableStateFlow<List<GetMainPageBannersQuery.Banner>?>(null)
    val offers = MutableStateFlow<List<GetMainPageOffersQuery.Offer>?>(null)
    val categories = MutableStateFlow<List<GetMainPageCategoriesQuery.Category>?>(null)

    fun getBanners() {
        screenModelScope.launch {
            fetchQuery(GetMainPageBannersQuery()) { data ->
                banners.update {
                    data.dshopMain.banners
                }
            }
        }
    }

    fun getOffers() {
        screenModelScope.launch {
            fetchQuery(GetMainPageOffersQuery()) { data ->
                offers.update {
                    data.dshopMain.offers
                }
            }
        }
    }

    fun getCategories() {
        screenModelScope.launch {
            fetchQuery(GetMainPageCategoriesQuery()) { data ->
                categories.update {
                    data.dshopMain.categories
                }
            }
        }
    }

    private suspend fun <T : Query.Data> fetchQuery(query: Query<T>, onResult: (T) -> Unit) {
        val response = apolloClient.query(query).execute()

        if (response.hasErrors()) {
            response.errors!!.forEach { error ->
                appEventBus.emit(
                    AppEvent.ShowToast(
                        Toast(
                            message = error.message.ifEmpty {
                                context.resources.getString(R.string.app_someError)
                            }
                        )
                    ))
            }
        } else {
            try {
                val data = response.dataOrThrow()

                onResult(data)
            } catch (e: NoDataException) {
                appEventBus.emit(
                    AppEvent.ShowToast(
                        Toast(
                            message = context.resources.getString(R.string.app_someError)
                        )
                    ))
            }
        }
    }
}