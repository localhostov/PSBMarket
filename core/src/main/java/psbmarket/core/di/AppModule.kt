package psbmarket.core.di

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import psbmarket.core.Constants
import psbmarket.core.utils.ApolloTokenInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {
    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(Constants.BASE_GQL_URL)
            .interceptors(listOf(ApolloTokenInterceptor()))
            .build()
    }
}