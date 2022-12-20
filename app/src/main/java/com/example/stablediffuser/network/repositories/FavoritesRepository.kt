package com.example.stablediffuser.network.repositories

import android.content.SharedPreferences
import com.example.stablediffuser.ui.art.ArtData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.Json

private const val KEY_SHARED_PREF_FAVORITE = "stable_diffuser_favorite"

private const val KEY_SHARED_PREF_FAVORITE_COUNT = "stable_diffuser_favorite_count"

class FavoritesRepository(
    private val sharedPref: SharedPreferences
) {
    private val internalFavoritesFlow: MutableStateFlow<List<ArtData>> =
        MutableStateFlow(emptyList())

    val favoritesFlow: StateFlow<List<ArtData>> = internalFavoritesFlow

    fun addToFavorites(artData: ArtData) {
        internalFavoritesFlow.value = internalFavoritesFlow.value + artData
    }

    fun removeFromFavorites(artData: ArtData) {
        internalFavoritesFlow.value = internalFavoritesFlow.value.filter { favoriteArt ->
            favoriteArt.id == artData.id
        }
    }

    fun isFavorite(artData: ArtData): Boolean = favoritesFlow.value.any { favoriteArt ->
        favoriteArt.id == artData.id
    }

    private val lexicaJson by lazy {
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }

    fun save() {
        val favoriteArts = internalFavoritesFlow.value
        with(sharedPref.edit()) {
            repeat((favoriteArts.indices).count()) { index ->
                val artData: ArtData = favoriteArts[index]
                val artJson = lexicaJson.encodeToString(ArtData.serializer(), artData)
                putString("${KEY_SHARED_PREF_FAVORITE}_$index", artJson)
            }
            putInt(KEY_SHARED_PREF_FAVORITE_COUNT, favoriteArts.size)
            apply()
        }
    }

    fun restore() {
        internalFavoritesFlow.value = emptyList()

        val artsData = mutableListOf<ArtData>()

        with(sharedPref) {
            val queryCount = getInt(KEY_SHARED_PREF_FAVORITE_COUNT, 0)
            repeat((0 until queryCount).count()) { index ->
                getString("${KEY_SHARED_PREF_FAVORITE}_$index", null)?.also { artJson ->
                    val artData: ArtData =
                        lexicaJson.decodeFromString(ArtData.serializer(), artJson)
                    artsData += artData
                }
            }
        }

        internalFavoritesFlow.value = artsData
    }
}