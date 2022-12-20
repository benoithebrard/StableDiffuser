package com.example.stablediffuser.network.repositories

import android.content.SharedPreferences
import com.example.stablediffuser.network.lexica.LexicaImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FavoritesRepository(
    private val sharedPref: SharedPreferences
) {
    private val internalFavoritesFlow: MutableStateFlow<List<LexicaImage>> =
        MutableStateFlow(emptyList())

    val favoritesFlow: StateFlow<List<LexicaImage>> = internalFavoritesFlow

    fun addToFavorites(image: LexicaImage) {
        internalFavoritesFlow.value = internalFavoritesFlow.value + image
    }

    fun removeFromFavorites(image: LexicaImage) {
        internalFavoritesFlow.value = internalFavoritesFlow.value.filter { favoriteImage ->
            favoriteImage.id == image.id
        }
    }

    /*
     lexicaJson.decodeFromString<>()
        lexicaJson.encodeToString( LexicaImage.serializer(), LexicaImage())
     */
}