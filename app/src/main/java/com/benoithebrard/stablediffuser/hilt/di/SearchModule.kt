package com.benoithebrard.stablediffuser.hilt.di

import com.benoithebrard.stablediffuser.network.repositories.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

private const val LEXICA_BASE_URL = "https://lexica.art/api/"

@InstallIn(SingletonComponent::class)
@Module
object SearchModule {

    @Singleton
    @Provides
    fun provideSearchRepository(
        @DefaultHttpClientBuilder httpClientBuilder: OkHttpClient.Builder
    ): SearchRepository {
        return SearchRepository(
            baseUrl = LEXICA_BASE_URL,
            httpClientBuilder = httpClientBuilder
        )
    }
}

