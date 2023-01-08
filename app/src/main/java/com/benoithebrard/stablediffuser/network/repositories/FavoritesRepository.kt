package com.benoithebrard.stablediffuser.network.repositories

import android.content.SharedPreferences
import com.benoithebrard.stablediffuser.ui.art.ArtData
import com.benoithebrard.stablediffuser.utils.LexicaJson
import com.benoithebrard.stablediffuser.utils.extensions.containsArt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

private const val KEY_SHARED_PREF_FAVORITE = "stable_diffuser_favorite"
private const val KEY_SHARED_PREF_FAVORITE_COUNT = "stable_diffuser_favorite_count"

@Singleton
class FavoritesRepository @Inject constructor(
    private val sharedPref: SharedPreferences
) {
    private val internalFavoritesFlow: MutableStateFlow<List<ArtData>> =
        MutableStateFlow(emptyList())

    val favoritesFlow: StateFlow<List<ArtData>> = internalFavoritesFlow

    fun addToFavorites(artData: ArtData) {
        internalFavoritesFlow.value = listOf(artData) + internalFavoritesFlow.value
    }

    fun removeFromFavorites(artData: ArtData) {
        val filteredFavorites = internalFavoritesFlow.value.mapNotNull { favoriteArt ->
            favoriteArt.takeUnless { it.id == artData.id }
        }
        internalFavoritesFlow.value = filteredFavorites
    }

    fun isFavorite(artData: ArtData): Boolean = favoritesFlow.value.containsArt(artData)

    fun save() {
        val favoriteArts = internalFavoritesFlow.value

        with(sharedPref.edit()) {
            clear()
            repeat((favoriteArts.indices).count()) { index ->
                val artData: ArtData = favoriteArts[index]
                val artJson = LexicaJson.json.encodeToString(
                    serializer = ArtData.serializer(),
                    value = artData
                )
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
                    val artData: ArtData = LexicaJson.json.decodeFromString(
                        deserializer = ArtData.serializer(),
                        string = artJson
                    )
                    artsData += artData
                }
            }
        }

        internalFavoritesFlow.value = artsData
    }
}