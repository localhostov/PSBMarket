package me.localx.psbmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.util.fastForEach
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
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
import me.localx.psbmarket.ui.tabs.CartTab
import me.localx.psbmarket.ui.tabs.FavoritesTab
import me.localx.psbmarket.ui.tabs.HomeTab
import me.localx.psbmarket.ui.tabs.OverviewTab
import me.localx.psbmarket.ui.tabs.ProfileTab
import psbmarket.uikit.components.bottomBar.BottomBar
import psbmarket.uikit.components.bottomBar.BottomBarTabItem
import psbmarket.uikit.components.bottomBar.Tab
import psbmarket.uikit.theme.AppTheme
import psbmarket.uikit.theme.Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                scrim = Color.Transparent.toArgb(),
            ),
            navigationBarStyle = SystemBarStyle.dark(
                scrim = Color.Transparent.toArgb(),
            )
        )

        setContent {
            AppTheme {
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