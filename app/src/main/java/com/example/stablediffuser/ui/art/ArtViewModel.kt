package com.example.stablediffuser.ui.art

import android.content.ClipData
import android.content.ClipboardManager
import android.view.View
import androidx.databinding.ObservableBoolean

data class ArtViewModel(
    val imageUrl: String,
    val prompt: String,
    val dimensions: String,
    val nsfw: Boolean,
    val clipboard: ClipboardManager,
    val onShowMosaic: View.OnClickListener,
) {
    val showPrompt = ObservableBoolean(true)

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
}