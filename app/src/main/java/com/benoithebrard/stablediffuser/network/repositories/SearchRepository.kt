package com.benoithebrard.stablediffuser.network.repositories

import com.benoithebrard.stablediffuser.network.lexica.LexicaService
import com.benoithebrard.stablediffuser.ui.art.ArtData
import com.benoithebrard.stablediffuser.ui.art.toArtData
import com.benoithebrard.stablediffuser.utils.LexicaJson
import com.benoithebrard.stablediffuser.utils.extensions.toResult
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.io.IOException
import java.util.*

class SearchRepository(
    baseUrl: String,
    httpClientBuilder: OkHttpClient.Builder
) {
    private val artDataCache: WeakHashMap<String, List<ArtData>> = WeakHashMap()

    @OptIn(ExperimentalSerializationApi::class)
    private val lexicaService: LexicaService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClientBuilder.build())
            .addConverterFactory(
                LexicaJson.json.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(LexicaService::class.java)
    }

    suspend fun searchForQuery(
        query: String
    ): Result<List<ArtData>> = artDataCache[query]?.let { artData ->
        // return artData from cache
        Result.success(artData)
    } ?: try {
        // search for images
        lexicaService.searchForImages(query).toResult().map { searchResponse ->
            searchResponse.images.map { image ->
                image.toArtData()
            }
        }.also { result ->
            result.getOrNull()?.let { artData ->
                // store artData in cache
                artDataCache[query] = artData
            }
        }
    } catch (e: IOException) {
        Result.failure(e)
    }

}