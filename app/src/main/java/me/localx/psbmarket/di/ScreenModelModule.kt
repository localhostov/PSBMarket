package me.localx.psbmarket.di

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap
import me.localx.psbmarket.ui.screens.favorite.FavoriteScreenModel
import me.localx.psbmarket.ui.screens.home.HomeScreenModel
import me.localx.psbmarket.ui.screens.profile.ProfileScreenModel
import me.localx.psbmarket.ui.screens.signIn.SignInScreenModel

@Module
@InstallIn(ActivityComponent::class)
abstract class ScreenModelModule {
    @Binds
    @IntoMap
    @ScreenModelKey(ProfileScreenModel::class)
    abstract fun bindProfileScreenModel(screenModel: ProfileScreenModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(SignInScreenModel::class)
    abstract fun bindSignInScreenModel(screenModel: SignInScreenModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(FavoriteScreenModel::class)
    abstract fun bindFavoriteScreenModel(screenModel: FavoriteScreenModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(HomeScreenModel::class)
    abstract fun bindHomeScreenModel(screenModel: HomeScreenModel): ScreenModel
}