package com.benoithebrard.stablediffuser.ui.art

import android.content.ClipData
import android.content.ClipboardManager
import android.view.View
import androidx.databinding.ObservableBoolean
import com.benoithebrard.stablediffuser.network.repositories.FavoritesRepository

data class ArtViewModel(
    val artData: ArtData,
    val clipboard: ClipboardManager,
    val onShowMosaic: View.OnClickListener,
    val favoritesRepository: FavoritesRepository
) {
    val showPrompt = ObservableBoolean()

    val onTogglePrompt = View.OnClickListener {
        showPrompt.set(!showPrompt.get())
    }

    val onSharePrompt = View.OnClickListener {
        ClipData.newPlainText("Stable Diffusion prompt", artData.prompt).also { clipData ->
            clipboard.setPrimaryClip(clipData)
        }
    }

    val onShareUrl = View.OnClickListener {
        ClipData.newPlainText("Stable Diffusion url", artData.url).also { clipData ->
            clipboard.setPrimaryClip(clipData)
        }
    }

    val showAsFavorite = ObservableBoolean(favoritesRepository.isFavorite(artData))

    val onToggleFavorite = View.OnClickListener {
        if (favoritesRepository.isFavorite(artData)) {
            favoritesRepository.removeFromFavorites(artData)
        } else {
            favoritesRepository.addToFavorites(artData)
        }
    }
}