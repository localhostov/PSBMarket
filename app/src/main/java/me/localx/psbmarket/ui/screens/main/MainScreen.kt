package me.localx.psbmarket.ui.screens.main

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.util.fastForEach
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import kotlinx.coroutines.launch
import me.localx.icons.rounded.Icons
import me.localx.icons.rounded.filled.Apps
import me.localx.icons.rounded.filled.CartShoppingFast
import me.localx.icons.rounded.filled.Heart
import me.localx.icons.rounded.filled.Home
import me.localx.icons.rounded.filled.User
import me.localx.icons.rounded.outline.Apps
import me.localx.icons.rounded.outline.CartShoppingFast
import me.localx.icons.rounded.outline.Heart
import me.localx.icons.rounded.outline.Home
import me.localx.icons.rounded.outline.User
import me.localx.psbmarket.ui.screens.signIn.SignInScreen
import me.localx.psbmarket.ui.tabs.CartTab
import me.localx.psbmarket.ui.tabs.FavoritesTab
import me.localx.psbmarket.ui.tabs.HomeTab
import me.localx.psbmarket.ui.tabs.OverviewTab
import me.localx.psbmarket.ui.tabs.ProfileTab
import psbmarket.core.Preferences
import psbmarket.core.data.event.AppEvent
import psbmarket.core.data.event.LocalAppEventBus
import psbmarket.core.extensions.dataStore
import psbmarket.uikit.components.bottomBar.BottomBar
import psbmarket.uikit.components.bottomBar.BottomBarTabItem
import psbmarket.uikit.components.bottomBar.Tab
import psbmarket.uikit.theme.Theme

class MainScreen : Screen {
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow
        val appEventBus = LocalAppEventBus.current
        val lifecycleOwner  = LocalLifecycleOwner.current
        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            appEventBus.subscribe(lifecycleOwner.lifecycleScope) { event ->
                if (event is AppEvent.Logout) {
                    navigator.replaceAll(SignInScreen())
                    scope.launch {
                        context.dataStore.edit { prefs ->
                            prefs[Preferences.accessToken] = ""
                        }
                    }
                }
            }
        }

        TabNavigator(
            tab = HomeTab,
            tabDisposable = {
                TabDisposable(
                    navigator = it,
                    tabs = listOf(HomeTab, OverviewTab, CartTab, FavoritesTab, ProfileTab)
                )
            }
        ) { currentTab ->
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = Theme.colors.background,
                content = { innerPadding ->
                    val currentTabIndex = currentTab.current.options.index

                    AnimatedContent(
                        targetState = currentTab.current to currentTabIndex,
                        label = "tab transition",
                        transitionSpec = {
                            val animationSpec = spring(
                                stiffness = Spring.StiffnessMediumLow,
                                visibilityThreshold = IntOffset.VisibilityThreshold
                            )

                            val (initialOffset, targetOffset) = if (initialState.second > targetState.second) {
                                ({ size: Int -> -size }) to ({ size: Int -> size })
                            } else {
                                ({ size: Int -> size }) to ({ size: Int -> -size })
                            }

                            slideInHorizontally(animationSpec, initialOffset) togetherWith
                                    slideOutHorizontally(animationSpec, targetOffset)
                        }
                    ) { (tab, _) ->
                        currentTab.saveableState("transition", tab) {
                            Box(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())) {
                                tab.Content()
                            }
                        }
                    }
                },
                bottomBar = {
                    BottomBar {
                        tabs.fastForEach { tab ->
                            BottomBarTabItem(tab)
                        }
                    }
                }
            )
        }
    }
}

private val tabs
    @Composable
    get() = listOf(
        Tab(
            tab = HomeTab,
            inactiveIcon = Icons.Outline.Home,
            activeIcon = Icons.Filled.Home
        ),
        Tab(
            tab = OverviewTab,
            inactiveIcon = Icons.Outline.Apps,
            activeIcon = Icons.Filled.Apps
        ),
        Tab(
            tab = CartTab,
            inactiveIcon = Icons.Outline.CartShoppingFast,
            activeIcon = Icons.Filled.CartShoppingFast
        ),
        Tab(
            tab = FavoritesTab,
            inactiveIcon = Icons.Outline.Heart,
            activeIcon = Icons.Filled.Heart
        ),
        Tab(
            tab = ProfileTab,
            inactiveIcon = Icons.Outline.User,
            activeIcon = Icons.Filled.User
        ),
    )