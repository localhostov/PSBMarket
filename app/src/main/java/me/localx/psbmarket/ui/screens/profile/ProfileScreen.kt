package me.localx.psbmarket.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import kotlinx.coroutines.launch
import me.localx.psbmarket.R
import me.localx.psbmarket.ui.screens.profile.views.AvatarView
import me.localx.psbmarket.ui.screens.profile.views.NameView
import psbmarket.core.data.event.AppEvent
import psbmarket.core.data.event.LocalAppEventBus
import psbmarket.uikit.components.alert.Alert
import psbmarket.uikit.components.alert.rememberAlertState
import psbmarket.uikit.theme.Theme

class ProfileScreen : Screen {
    @Composable
    override fun Content() {
        val appEventBus = LocalAppEventBus.current
        val scope = rememberCoroutineScope()
        val logoutAlert = rememberAlertState()
        val screenModel: ProfileScreenModel = getScreenModel()

        LaunchedEffect(Unit) {
            screenModel.getUserData()
        }

        Alert(
            state = logoutAlert,
            title = stringResource(R.string.screen_profile_logoutAlertTitle),
            caption = stringResource(R.string.screen_profile_logoutAlertCaption),
            onNegativeButtonText = stringResource(R.string.screen_profile_logoutAlertNegative),
            onPositiveButtonText = stringResource(R.string.screen_profile_logoutAlertPositive),
            onNegativeButtonClick = {
                scope.launch {
                    appEventBus.emit(AppEvent.Logout)
                }
            }
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .statusBarsPadding()
        ) {
            AvatarView(screenModel)
            NameView(screenModel)

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Theme.colors.buttonPrimaryBackground
                )
            ) {
                Text(
                    text = stringResource(R.string.screen_profile_edit),
                    fontSize = 16.sp,
                    color = Theme.colors.buttonPrimaryForeground
                )
            }

            Box(modifier = Modifier.weight(1f))

            Button(
                onClick = { logoutAlert.show() },
                modifier = Modifier.fillMaxWidth(),
                shape = Theme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Theme.colors.error.copy(0.25f),
                )
            ) {
                Text(
                    text = stringResource(R.string.screen_profile_exit),
                    fontSize = 16.sp,
                    color = Theme.colors.error
                )
            }
        }
    }
}