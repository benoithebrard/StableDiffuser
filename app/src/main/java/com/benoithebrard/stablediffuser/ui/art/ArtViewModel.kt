package com.benoithebrard.stablediffuser.ui.art

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat.startActivity
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

    val onOpenWebBrowser = View.OnClickListener {
        Intent(Intent.ACTION_VIEW, Uri.parse(artData.url)).also { browserIntent ->
            startActivity(it.context, browserIntent, null)
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