package psbmarket.core.utils

import android.content.Context
import com.apollographql.apollo.api.ApolloRequest
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.interceptor.ApolloInterceptor
import com.apollographql.apollo.interceptor.ApolloInterceptorChain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import psbmarket.core.Constants
import psbmarket.core.Preferences
import psbmarket.core.extensions.dataStore

internal class ApolloTokenInterceptor(
    private val context: Context
) : ApolloInterceptor {
    override fun <D : Operation.Data> intercept(
        request: ApolloRequest<D>,
        chain: ApolloInterceptorChain
    ): Flow<ApolloResponse<D>> = flow {
        val accessToken = context.dataStore.data.first()[Preferences.accessToken] ?: Constants.BASIC_API_TOKEN
        val modifiedRequest = request
            .newBuilder()
            .addHttpHeader("Authorization", accessToken)
            .build()

        emitAll(chain.proceed(modifiedRequest))
    }
}