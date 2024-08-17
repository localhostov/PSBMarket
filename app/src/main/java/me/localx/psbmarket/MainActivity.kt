package me.localx.psbmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import me.localx.psbmarket.ui.screens.main.MainScreen
import me.localx.psbmarket.ui.screens.signIn.SignInScreen
import psbmarket.core.Preferences
import psbmarket.core.data.event.AppEvent
import psbmarket.core.data.event.AppEventBus
import psbmarket.core.data.event.LocalAppEventBus
import psbmarket.core.extensions.dataStore
import psbmarket.uikit.theme.AppTheme
import psbmarket.uikit.theme.Theme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var appEventBus: AppEventBus
    private val accessToken by lazy {
        runBlocking {
            dataStore.data.first()[Preferences.accessToken] ?: ""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                scrim = Color.Transparent.toArgb(),
            ),
            navigationBarStyle = SystemBarStyle.dark(
                scrim = Color.Transparent.toArgb(),
            )
        )

        val initialScreen = if (accessToken.isEmpty()) {
            SignInScreen()
        } else {
            MainScreen()
        }

        setContent {
            val toaster = rememberToasterState()

            LaunchedEffect(Unit) {
                appEventBus.subscribe(lifecycleScope) { event ->
                    if (event is AppEvent.ShowToast) {
                        toaster.show(event.toast)
                    }
                }
            }

            CompositionLocalProvider(
                LocalAppEventBus provides appEventBus
            ) {
                AppTheme {
                    Toaster(
                        state = toaster,
                        background = { SolidColor(Theme.colors.toasterBackground) },
                        shadowAmbientColor = Color.Transparent,
                        shadowSpotColor = Color.Transparent,
                        border = { BorderStroke(1.dp, Theme.colors.toasterBorder) },
                        shape = { Theme.shapes.medium },
                        contentColor = { Theme.colors.text }
                    )

                    Navigator(initialScreen) { navigator ->
                        SlideTransition(navigator)
                    }
                }
            }
        }
    }
}