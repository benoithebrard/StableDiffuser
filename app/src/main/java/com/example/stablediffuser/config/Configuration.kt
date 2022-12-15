package com.example.stablediffuser.config

import android.content.Context
import com.example.stablediffuser.network.interceptors.MockingInterceptor
import com.example.stablediffuser.network.repositories.SearchRepository
import com.example.stablediffuser.utils.QueryGenerator.PROMPT_EXAMPLE_1
import com.example.stablediffuser.utils.QueryGenerator.PROMPT_EXAMPLE_2
import com.example.stablediffuser.utils.QueryGenerator.PROMPT_EXAMPLE_3
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.net.URLEncoder

private const val LEXICA_BASE_URL = "https://lexica.art/api/"

private const val MAX_CHAR_COUNT_URL_MATCHER = 15

// TODO: use Hilt instead
object Configuration {

    lateinit var provideAppContext: () -> Context

    private val mockingInterceptor: Interceptor by lazy {
        MockingInterceptor(
            listOf(
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(PROMPT_EXAMPLE_1.urlMatcher)
                    },
                    jsonFileName = "search_response1.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(PROMPT_EXAMPLE_2.urlMatcher)
                    },
                    jsonFileName = "search_response2.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(PROMPT_EXAMPLE_3.urlMatcher)
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

    val searchRepository: SearchRepository by lazy {
        SearchRepository(
            baseUrl = LEXICA_BASE_URL,
            httpClientBuilder = httpClientBuilder
        )
    }

    private val String.urlMatcher: String
        // replace spaces by +, then by %20
        get() = URLEncoder.encode(this, "UTF-8").replace("+", "%20")

}