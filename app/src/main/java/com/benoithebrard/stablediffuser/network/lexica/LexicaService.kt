package com.benoithebrard.stablediffuser.network.lexica

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LexicaService {

    @GET("v1/search")
    suspend fun searchForImages(@Query("q") query: String): Response<LexicaSearchResponse>
}