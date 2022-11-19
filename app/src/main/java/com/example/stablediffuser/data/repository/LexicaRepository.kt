package com.example.stablediffuser.data.repository

import com.example.stablediffuser.data.image.LexicaImage
import com.example.stablediffuser.data.service.LexicaService
import com.example.stablediffuser.utils.extensions.toResult
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.io.IOException

class LexicaRepository(
    baseUrl: String,
    httpClientBuilder: OkHttpClient.Builder
) {
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
    ): Result<List<LexicaImage>> {
        return try {
            lexicaService.searchForImages(query).toResult().map { searchResponse ->
                searchResponse.images
            }
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}