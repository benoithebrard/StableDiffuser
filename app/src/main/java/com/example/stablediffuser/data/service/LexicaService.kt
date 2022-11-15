package com.example.stablediffuser.data.service

import com.example.stablediffuser.data.image.LexicaSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LexicaService {

    @GET("v1/search")
    suspend fun searchForImages(@Query("q") query: String): Response<LexicaSearchResponse>
}