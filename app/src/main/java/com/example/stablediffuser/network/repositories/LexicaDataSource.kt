package com.example.stablediffuser.network.repositories

import com.example.stablediffuser.network.entities.LexicaImage

fun interface LexicaDataSource {

    suspend fun fetchLexicaImages(
        searchQuery: String
    ): Result<List<LexicaImage>>
}