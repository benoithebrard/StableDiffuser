package com.benoithebrard.stablediffuser.config

import android.content.Context
import com.benoithebrard.stablediffuser.network.repositories.QueryRepository
import com.benoithebrard.stablediffuser.network.repositories.SearchRepository
import com.benoithebrard.stablediffuser.utils.MockingInterceptor
import com.benoithebrard.stablediffuser.utils.PromptGenerator.PROMPT_EXAMPLE_1
import com.benoithebrard.stablediffuser.utils.PromptGenerator.PROMPT_EXAMPLE_10
import com.benoithebrard.stablediffuser.utils.PromptGenerator.PROMPT_EXAMPLE_11
import com.benoithebrard.stablediffuser.utils.PromptGenerator.PROMPT_EXAMPLE_2
import com.benoithebrard.stablediffuser.utils.PromptGenerator.PROMPT_EXAMPLE_3
import com.benoithebrard.stablediffuser.utils.PromptGenerator.PROMPT_EXAMPLE_4
import com.benoithebrard.stablediffuser.utils.PromptGenerator.PROMPT_EXAMPLE_5
import com.benoithebrard.stablediffuser.utils.PromptGenerator.PROMPT_EXAMPLE_6
import com.benoithebrard.stablediffuser.utils.PromptGenerator.PROMPT_EXAMPLE_7
import com.benoithebrard.stablediffuser.utils.PromptGenerator.PROMPT_EXAMPLE_8
import com.benoithebrard.stablediffuser.utils.PromptGenerator.PROMPT_EXAMPLE_9
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.net.URLEncoder

private const val LEXICA_BASE_URL = "https://lexica.art/api/"

private const val PROMPT_RETRY_LATER = "retry later"

private const val PROMPT_EMPTY_SEARCH = "empty search"

object Configuration {

    const val CHARSET_UTF_8 = "UTF-8"
    const val HTTP_CODE_OK = 200
    const val HTTP_ERROR_TOO_MANY_REQUESTS = 429
    const val HTTP_HEADER_RETRY_AFTER = "retry-after"

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
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(PROMPT_EXAMPLE_4.urlMatcher)
                    },
                    jsonFileName = "search_response4.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(PROMPT_EXAMPLE_5.urlMatcher)
                    },
                    jsonFileName = "search_response5.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(PROMPT_EXAMPLE_6.urlMatcher)
                    },
                    jsonFileName = "search_response6.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(PROMPT_EXAMPLE_7.urlMatcher)
                    },
                    jsonFileName = "search_response7.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(PROMPT_EXAMPLE_8.urlMatcher)
                    },
                    jsonFileName = "search_response8.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(PROMPT_EXAMPLE_9.urlMatcher)
                    },
                    jsonFileName = "search_response9.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(PROMPT_EXAMPLE_10.urlMatcher)
                    },
                    jsonFileName = "search_response10.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(PROMPT_EXAMPLE_11.urlMatcher)
                    },
                    jsonFileName = "search_response11.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(PROMPT_EMPTY_SEARCH.urlMatcher)
                    },
                    jsonFileName = "search_response0.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        val result = url.contains(PROMPT_RETRY_LATER.urlMatcher)
                        result
                    },
                    jsonFileName = "search_response8.json",
                    errorCode = HTTP_ERROR_TOO_MANY_REQUESTS,
                    header = HTTP_HEADER_RETRY_AFTER to "2000"
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

    val queryRepository: QueryRepository by lazy {
        QueryRepository(
            sharedPref = provideAppContext().getSharedPreferences(
                "StableDiffuser_Query",
                Context.MODE_PRIVATE
            )
        )
    }

    private val String.urlMatcher: String
        // replace spaces by +, then by %20
        get() = URLEncoder.encode(this, "UTF-8").replace("+", "%20")

}