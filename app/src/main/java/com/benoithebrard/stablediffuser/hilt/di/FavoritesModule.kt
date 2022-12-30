package com.benoithebrard.stablediffuser.hilt.di

import android.content.Context
import android.content.SharedPreferences
import com.benoithebrard.stablediffuser.network.repositories.FavoritesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FavoritesModule {

    @Singleton
    @Provides
    fun provideFavoritesRepository(
        sharedPref: SharedPreferences
    ): FavoritesRepository {
        return FavoritesRepository(sharedPref)
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext appContext: Context
    ): SharedPreferences {
        return appContext.getSharedPreferences(
            "StableDiffuser_Favorites",
            Context.MODE_PRIVATE
        )
    }
}