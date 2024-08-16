package me.localx.psbmarket.ui.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import me.localx.psbmarket.ui.screens.cart.CartScreen

object CartTab : Tab {
    private fun readResolve(): Any = HomeTab

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 2u,
            title = ""
        )

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        Navigator(
            screen = CartScreen(),
            disposeBehavior = NavigatorDisposeBehavior(disposeSteps = false),
        ) { navigator ->
            SlideTransition(
                navigator = navigator,
                disposeScreenAfterTransitionEnd = true
            )
        }
    }
}