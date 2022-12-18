package com.example.stablediffuser.network.repositories

import com.example.stablediffuser.network.entities.LexicaImage

class FavoritesRepository : LexicaDataSource {

    override suspend fun fetchLexicaImages(searchQuery: String): Result<List<LexicaImage>> {
        return Result.success(emptyList())
    }
}