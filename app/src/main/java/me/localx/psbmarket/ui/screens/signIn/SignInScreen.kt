package me.localx.psbmarket.ui.screens.signIn

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import me.localx.psbmarket.R
import me.localx.psbmarket.ui.screens.signIn.views.LogoView
import me.localx.psbmarket.ui.screens.signIn.views.TextFieldsView
import psbmarket.uikit.theme.Theme

class SignInScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: SignInScreenModel = getScreenModel()
        val state by screenModel.state.collectAsState()

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.background)
                .padding(16.dp)
                .navigationBarsPadding()
                .statusBarsPadding()
                .imePadding()
        ) {
            LogoView()

            Text(
                text = stringResource(R.string.screen_signIn_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            TextFieldsView(screenModel)

            Text(
                text = stringResource(R.string.screen_signIn_resetPassword),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            )

            Button(
                enabled = !state.isSigningIn,
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(),
                shape = Theme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Theme.colors.accent,
                    contentColor = Theme.colors.text,
                    disabledContentColor = Theme.colors.text,
                    disabledContainerColor = Theme.colors.accent.copy(alpha = 0.75f)
                ),
                onClick = { screenModel.signIn(navigator) }
            ) {
                AnimatedContent(
                    targetState = state.isSigningIn,
                    label = "loading",
                    contentAlignment = Alignment.Center,
                    transitionSpec = {
                        fadeIn() + scaleIn() togetherWith fadeOut() + scaleOut() using SizeTransform(clip = false)
                    }
                ) { isLoading ->
                    if (isLoading) {
                        CircularProgressIndicator(
                            strokeCap = StrokeCap.Round,
                            strokeWidth = 2.dp,
                            color = LocalContentColor.current,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text(
                            text = stringResource(R.string.screen_signIn_signInAction),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = LocalContentColor.current
                        )
                    }
                }
            }
        }
    }
}