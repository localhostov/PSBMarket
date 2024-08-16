package psbmarket.core.utils

import com.apollographql.apollo.api.ApolloRequest
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.interceptor.ApolloInterceptor
import com.apollographql.apollo.interceptor.ApolloInterceptorChain
import kotlinx.coroutines.flow.Flow
import psbmarket.core.Constants

internal class ApolloTokenInterceptor : ApolloInterceptor {
    override fun <D : Operation.Data> intercept(
        request: ApolloRequest<D>,
        chain: ApolloInterceptorChain
    ): Flow<ApolloResponse<D>> {
        val modifiedRequest = request
            .newBuilder()
            .addHttpHeader("Authorization", Constants.BASIC_API_TOKEN)
            .build()

        return chain.proceed(modifiedRequest)
    }
}