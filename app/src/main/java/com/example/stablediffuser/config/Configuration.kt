package com.example.stablediffuser.config

import android.content.Context
import com.example.stablediffuser.data.repositories.LexicaRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient

private const val LEXICA_BASE_URL = "https://lexica.art/api/"

// TODO: use Hilt instead
object Configuration {

    lateinit var provideAppContext: () -> Context

    private val mockingInterceptor: Interceptor by lazy {
        MockingInterceptor(
            listOf(
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains("mocking1")
                    },
                    jsonFileName = "search_response1.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains("mocking2")
                    },
                    jsonFileName = "search_response2.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains("mocking3")
                    },
                    jsonFileName = "search_response3.json"
                )
            )
        )
    }

    private val httpClientBuilder: OkHttpClient.Builder by lazy {
        OkHttpClient().newBuilder()
            .addInterceptor(mockingInterceptor)
        // add more network interceptors here
    }

    val lexicaRepository: LexicaRepository by lazy {
        LexicaRepository(
            baseUrl = LEXICA_BASE_URL,
            httpClientBuilder = httpClientBuilder
        )
    }
}