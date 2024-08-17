package psbmarket.core.di

import android.content.Context
import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import psbmarket.core.Constants
import psbmarket.core.data.event.AppEventBus
import psbmarket.core.utils.ApolloTokenInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {
    @Provides
    @Singleton
    fun provideApolloClient(@ApplicationContext context: Context): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(Constants.BASE_GQL_URL)
            .interceptors(listOf(ApolloTokenInterceptor(context)))
            .build()
    }

    @Provides
    @Singleton
    fun provideAppEventBus(): AppEventBus {
        return AppEventBus()
    }
}