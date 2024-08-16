package me.localx.psbmarket.ui.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import me.localx.psbmarket.ui.screens.profile.ProfileScreen

object ProfileTab : Tab {
    private fun readResolve(): Any = HomeTab

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 4u,
            title = ""
        )

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        Navigator(
            screen = ProfileScreen(),
            disposeBehavior = NavigatorDisposeBehavior(disposeSteps = false),
        ) { navigator ->
            SlideTransition(
                navigator = navigator,
                disposeScreenAfterTransitionEnd = true
            )
        }
    }
}