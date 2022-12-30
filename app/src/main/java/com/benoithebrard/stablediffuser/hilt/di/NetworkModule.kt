package com.benoithebrard.stablediffuser.hilt.di

import android.content.Context
import com.benoithebrard.stablediffuser.utils.MockingInterceptor
import com.benoithebrard.stablediffuser.utils.PromptGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.net.URLEncoder
import javax.inject.Qualifier
import javax.inject.Singleton

private const val LEXICA_BASE_URL = "https://lexica.art/api/"
private const val PROMPT_RETRY_LATER = "retry later"
private const val PROMPT_EMPTY_SEARCH = "empty search"

internal const val HTTP_ERROR_TOO_MANY_REQUESTS = 429
internal const val HTTP_HEADER_RETRY_AFTER = "retry-after"

@Qualifier
annotation class SearchMockingInterceptor

@Qualifier
annotation class DefaultHttpClientBuilder

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @DefaultHttpClientBuilder
    @Singleton
    @Provides
    fun provideHttpClientBuilder(
        @SearchMockingInterceptor mockingInterceptor: Interceptor
    ): OkHttpClient.Builder {
        return OkHttpClient().newBuilder()
            .addInterceptor(mockingInterceptor)
        // add more network interceptors here
    }

    @SearchMockingInterceptor
    @Singleton
    @Provides
    fun provideHttpInterceptor(
        @ApplicationContext appContext: Context
    ): Interceptor {
        return MockingInterceptor(
            appContext = appContext,
            mockings = listOf(
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(PromptGenerator.PROMPT_EXAMPLE_1.urlMatcher)
                    },
                    jsonFileName = "search_response1.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(PromptGenerator.PROMPT_EXAMPLE_2.urlMatcher)
                    },
                    jsonFileName = "search_response2.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(PromptGenerator.PROMPT_EXAMPLE_3.urlMatcher)
                    },
                    jsonFileName = "search_response3.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(PromptGenerator.PROMPT_EXAMPLE_4.urlMatcher)
                    },
                    jsonFileName = "search_response4.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(PromptGenerator.PROMPT_EXAMPLE_5.urlMatcher)
                    },
                    jsonFileName = "search_response5.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(PromptGenerator.PROMPT_EXAMPLE_6.urlMatcher)
                    },
                    jsonFileName = "search_response6.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(PromptGenerator.PROMPT_EXAMPLE_7.urlMatcher)
                    },
                    jsonFileName = "search_response7.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(PromptGenerator.PROMPT_EXAMPLE_8.urlMatcher)
                    },
                    jsonFileName = "search_response8.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(PromptGenerator.PROMPT_EXAMPLE_9.urlMatcher)
                    },
                    jsonFileName = "search_response9.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(PromptGenerator.PROMPT_EXAMPLE_10.urlMatcher)
                    },
                    jsonFileName = "search_response10.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(PromptGenerator.PROMPT_EXAMPLE_11.urlMatcher)
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
                        url.contains(PROMPT_RETRY_LATER.urlMatcher)
                    },
                    jsonFileName = "search_response8.json",
                    errorCode = HTTP_ERROR_TOO_MANY_REQUESTS,
                    header = HTTP_HEADER_RETRY_AFTER to "2000"
                )
            )
        )
    }
}

private val String.urlMatcher: String
    // replace spaces by +, then by %20
    get() = URLEncoder.encode(this, "UTF-8").replace("+", "%20")
