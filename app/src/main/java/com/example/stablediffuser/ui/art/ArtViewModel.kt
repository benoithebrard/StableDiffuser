package com.example.stablediffuser.ui.art

import android.content.ClipData
import android.content.ClipboardManager
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.delay

private const val DELAY_MS_SHOW_PROMPT = 1500L

data class ArtViewModel(
    val imageUrl: String,
    val thumbUrl: String,
    val prompt: String,
    val dimensions: String,
    val nsfw: Boolean,
    val clipboard: ClipboardManager,
    val scope: LifecycleCoroutineScope,
    val onShowMosaic: View.OnClickListener,
) {
    val showPrompt = ObservableBoolean()

    val onTogglePrompt = View.OnClickListener {
        showPrompt.set(!showPrompt.get())
    }

    val onSharePrompt = View.OnClickListener {
        ClipData.newPlainText("Stable Diffusion prompt", prompt).also { clipData ->
            clipboard.setPrimaryClip(clipData)
        }
    }

    val onShareUrl = View.OnClickListener {
        ClipData.newPlainText("Stable Diffusion url", imageUrl).also { clipData ->
            clipboard.setPrimaryClip(clipData)
        }
    }

    init {
        scope.launchWhenCreated {
            delay(DELAY_MS_SHOW_PROMPT)
            showPrompt.set(true)
        }
    }
}