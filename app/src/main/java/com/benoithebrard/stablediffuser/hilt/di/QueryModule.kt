package com.benoithebrard.stablediffuser.hilt.di

import android.content.SharedPreferences
import com.benoithebrard.stablediffuser.network.repositories.QueryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object QueryModule {

    @Singleton
    @Provides
    fun provideQueryRepository(
        @QueryPreferences sharedPref: SharedPreferences
    ): QueryRepository {
        return QueryRepository(sharedPref)
    }
}