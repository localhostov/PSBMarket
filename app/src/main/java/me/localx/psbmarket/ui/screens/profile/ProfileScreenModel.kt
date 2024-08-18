package me.localx.psbmarket.ui.screens.profile

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.exception.NoDataException
import com.dokar.sonner.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import me.localx.psbmarket.R
import psbmarket.core.data.event.AppEvent
import psbmarket.core.data.event.AppEventBus
import psbmarket.graphql.GetUserDataQuery
import javax.inject.Inject

class ProfileScreenModel @Inject constructor(
    private val appEventBus: AppEventBus,
    private val apolloClient: ApolloClient,
    @ApplicationContext private val context: Context
) : ScreenModel {
    var user by mutableStateOf<GetUserDataQuery.User?>(null)
        private set

    fun getUserData() {
        screenModelScope.launch {
            val response = apolloClient.query(GetUserDataQuery()).execute()

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

                    user = data.user
                } catch (e: NoDataException) {
                    appEventBus.emit(AppEvent.ShowToast(
                        Toast(
                            message = context.resources.getString(R.string.app_someError)
                        )
                    ))
                }
            }
        }
    }
}