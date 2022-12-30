package com.benoithebrard.stablediffuser.hilt.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class FavoritesPreferences

@Qualifier
annotation class QueryPreferences

@InstallIn(SingletonComponent::class)
@Module
object SharedPreferencesModule {

    @FavoritesPreferences
    @Singleton
    @Provides
    fun provideFavoritesPreferences(
        @ApplicationContext appContext: Context
    ): SharedPreferences {
        return appContext.getSharedPreferences(
            "StableDiffuser_Favorites",
            Context.MODE_PRIVATE
        )
    }

    @QueryPreferences
    @Singleton
    @Provides
    fun provideQueryPreferences(
        @ApplicationContext appContext: Context
    ): SharedPreferences {
        return appContext.getSharedPreferences(
            "StableDiffuser_Query",
            Context.MODE_PRIVATE
        )
    }
}