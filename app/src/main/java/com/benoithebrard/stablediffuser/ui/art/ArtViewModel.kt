package com.benoithebrard.stablediffuser.ui.art

import android.content.ClipData
import android.content.ClipboardManager
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.benoithebrard.stablediffuser.network.repositories.FavoritesRepository
import com.benoithebrard.stablediffuser.utils.extensions.containsArt
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val DELAY_MS_SHOW_PROMPT = 1000L

data class ArtViewModel(
    val artData: ArtData,
    val clipboard: ClipboardManager,
    val onShowMosaic: View.OnClickListener,
    val lifecycleOwner: LifecycleOwner,
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

    init {
        with(lifecycleOwner) {
            lifecycleScope.launchWhenCreated {
                delay(DELAY_MS_SHOW_PROMPT)
                showPrompt.set(true)
            }
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    favoritesRepository.favoritesFlow.collectLatest { favoriteArts ->
                        showAsFavorite.set(favoriteArts.containsArt(artData))
                    }
                }
            }
        }
    }
}