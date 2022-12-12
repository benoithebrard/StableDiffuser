package com.example.stablediffuser.data.repositories

import com.example.stablediffuser.data.lexica.LexicaImage
import com.example.stablediffuser.data.lexica.LexicaService
import com.example.stablediffuser.utils.extensions.toResult
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.io.IOException
import java.util.*

class SearchRepository(
    baseUrl: String,
    httpClientBuilder: OkHttpClient.Builder
) {
    private val searchCache: WeakHashMap<String, List<LexicaImage>> = WeakHashMap()

    private val lexicaJson by lazy {
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private val lexicaService: LexicaService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClientBuilder.build())
            .addConverterFactory(
                lexicaJson.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(LexicaService::class.java)
    }

    suspend fun searchForImages(
        query: String
    ): Result<List<LexicaImage>> = searchCache[query]?.let { images ->
        // return images from cache
        Result.success(images)
    } ?: try {
        // search for images
        lexicaService.searchForImages(query).toResult().map { searchResponse ->
            searchResponse.images
        }.also { result ->
            result.getOrNull()?.let { images ->
                // store images in cache
                searchCache[query] = images
            }
        }
    } catch (e: IOException) {
        Result.failure(e)
    }

}