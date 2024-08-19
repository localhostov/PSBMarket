package me.localx.psbmarket.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import me.localx.psbmarket.ui.screens.home.views.BannersView
import me.localx.psbmarket.ui.screens.home.views.CategoriesView
import me.localx.psbmarket.ui.screens.home.views.OffersView
import me.localx.psbmarket.ui.screens.home.views.SearchButtonView

class HomeScreen : Screen {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val screenModel: HomeScreenModel = getScreenModel()

        LaunchedEffect(Unit) {
            screenModel.getBanners()
            screenModel.getOffers()
            screenModel.getCategories()
        }

        LazyColumn(
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            stickyHeader { SearchButtonView() }

            item { BannersView(screenModel) }
            item { CategoriesView(screenModel) }
            item { OffersView(screenModel) }
        }
    }
}