package me.localx.psbmarket.ui.screens.signIn

import android.content.Context
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.edit
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.exception.NoDataException
import com.dokar.sonner.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.localx.psbmarket.R
import me.localx.psbmarket.ui.screens.main.MainScreen
import psbmarket.core.Preferences
import psbmarket.core.data.event.AppEvent
import psbmarket.core.data.event.AppEventBus
import psbmarket.core.extensions.dataStore
import psbmarket.graphql.SignInMutation
import javax.inject.Inject

@Immutable
class SignInScreenModel @Inject constructor(
    private val apolloClient: ApolloClient,
    private val appEventBus: AppEventBus,
    @ApplicationContext private val context: Context
) : ScreenModel {
    val state = MutableStateFlow(SignInScreenState())

    var phoneNumber by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    fun updatePhoneNumber(newValue: String) {
        phoneNumber = newValue
    }

    fun updatePassword(newValue: String) {
        password = newValue
    }

    fun signIn(navigator: Navigator) {
        state.update { it.copy(isSigningIn = true) }

        screenModelScope.launch {
            val response = apolloClient.mutation(
                SignInMutation(
                    password = password,
                    username = phoneNumber
                )
            ).execute()

            if (response.hasErrors()) {
                response.errors!!.forEach { error ->
                    appEventBus.emit(AppEvent.ShowToast(
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

                    context.dataStore.edit {
                        it[Preferences.accessToken] = "Bearer ${data.signIn.accessToken}"
                    }

                    navigator.replaceAll(MainScreen())
                } catch (e: NoDataException) {
                    appEventBus.emit(AppEvent.ShowToast(
                        Toast(
                            message = context.resources.getString(R.string.app_someError)
                        )
                    ))
                }
            }

            state.update { it.copy(isSigningIn = false) }
        }
    }
}