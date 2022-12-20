package com.example.stablediffuser.ui.art

import android.content.ClipData
import android.content.ClipboardManager
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.stablediffuser.config.Configuration
import com.example.stablediffuser.network.repositories.FavoritesRepository
import kotlinx.coroutines.delay

private const val DELAY_MS_SHOW_PROMPT = 1000L

data class ArtViewModel(
    val artData: ArtData,
    val clipboard: ClipboardManager,
    val scope: LifecycleCoroutineScope,
    val onShowMosaic: View.OnClickListener,
) {
    private val favoritesRepository: FavoritesRepository by lazy {
        Configuration.favoritesRepository
    }

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

    val isFavorite = ObservableBoolean(favoritesRepository.isFavorite(artData))

    val onToggleFavorite = View.OnClickListener {
        favoritesRepository.addToFavorites(artData)
    }

    init {
        scope.launchWhenCreated {
            delay(DELAY_MS_SHOW_PROMPT)
            showPrompt.set(true)
        }

    }
}