package com.benoithebrard.stablediffuser.hilt.di

import android.content.SharedPreferences
import com.benoithebrard.stablediffuser.network.repositories.FavoritesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FavoritesModule {

    @Singleton
    @Provides
    fun provideFavoritesRepository(
        @FavoritesPreferences sharedPref: SharedPreferences
    ): FavoritesRepository {
        return FavoritesRepository(sharedPref)
    }
}