package com.example.stablediffuser.config

import android.content.Context
import com.example.stablediffuser.data.repositories.LexicaRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient

private const val LEXICA_BASE_URL = "https://lexica.art/api/"

private const val SEARCH_REQUEST_1 =
    "a%20koala%20in%20a%20astronaut%20suit%2C%203d%2C%20sci-fi%20fantasy%2C%20intricate%2C%20elegant%2C%20highly%20detailed%2C%20lifelike%2C%20photorealistic%2C%20digital%20painting%2C%20artstation%2C%20illustration%2C%20concept%20art%2C%20sharp%20focus%2C%20art%20in%20the%20style%20of%20Shigenori%20Soejima"

private const val SEARCH_REQUEST_2 =
    "tattoo%20design%2C%20stencil%2C%20beautiful%20portrait%20of%20a%20girl%20with%20flowers%20in%20her%20hair%2C%20upper%20body%2C%20by%20artgerm%2C%20artgerm%2C%20digital%20art%2C%20cat%20girl"

private const val SEARCH_REQUEST_3 =
    "futuristic%20building%2C%20illustration%20by%20nicolas%20delort%2C%20detailed%2C%20sharp%2C%208%20k"

// TODO: use Hilt instead
object Configuration {

    lateinit var provideAppContext: () -> Context

    private val mockingInterceptor: Interceptor by lazy {
        MockingInterceptor(
            listOf(
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(SEARCH_REQUEST_1)
                    },
                    jsonFileName = "search_response1.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(SEARCH_REQUEST_2)
                    },
                    jsonFileName = "search_response2.json"
                ),
                MockingInterceptor.Mocking(
                    urlMatcher = { url ->
                        url.contains(SEARCH_REQUEST_3)
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