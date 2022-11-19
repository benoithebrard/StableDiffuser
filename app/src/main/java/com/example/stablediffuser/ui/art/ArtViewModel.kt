package com.example.stablediffuser.ui.art

import android.content.ClipData
import android.content.ClipboardManager
import android.view.View
import androidx.databinding.ObservableBoolean

data class ArtViewModel(
    val imageUrl: String,
    val prompt: String,
    val clipboard: ClipboardManager,
    val onShowMosaic: View.OnClickListener,
) {
    val showPrompt = ObservableBoolean()

    val onTogglePrompt = View.OnClickListener {
        showPrompt.set(!showPrompt.get())
    }

    val onSharePrompt = View.OnClickListener {
        val clip: ClipData = ClipData.newPlainText("Stable Diffusion prompt", prompt)
        clipboard.setPrimaryClip(clip)
    }
}