package me.localx.psbmarket.ui.screens.overview

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.apollographql.apollo.ApolloClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import psbmarket.graphql.GetCategoriesQuery
import javax.inject.Inject

class OverviewScreenModel @Inject constructor(
    private val apolloClient: ApolloClient,
) : ScreenModel {
    val categories = MutableStateFlow<List<GetCategoriesQuery.Category>?>(null)

    fun getCategories() {
        screenModelScope.launch {
            val response = apolloClient.query(GetCategoriesQuery()).execute()

            categories.update {
                response.dataAssertNoErrors.dshopMain.categories
            }
        }
    }
}