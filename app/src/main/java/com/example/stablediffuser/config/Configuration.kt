package com.example.stablediffuser.config

import com.example.stablediffuser.data.repository.LexicaRepository
import okhttp3.OkHttpClient

private const val LEXICA_BASE_URL = "https://lexica.art/api/"

// TODO: use Hilt instead
object Configuration {

    private val httpClientBuilder: OkHttpClient.Builder by lazy {
        OkHttpClient().newBuilder()
        // .addInterceptor
    }

    val lexicaRepository: LexicaRepository by lazy {
        LexicaRepository(
            baseUrl = LEXICA_BASE_URL,
            httpClientBuilder = httpClientBuilder
        )
    }
}