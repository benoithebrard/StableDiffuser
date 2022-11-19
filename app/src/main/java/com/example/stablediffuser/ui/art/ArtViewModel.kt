package com.example.stablediffuser.ui.art

import android.view.View
import androidx.databinding.ObservableBoolean

data class ArtViewModel(
    val imageUrl: String,
    val prompt: String,
    val onShowMosaic: View.OnClickListener,
) {
    val showPrompt = ObservableBoolean()

    val onToggleInfo = View.OnClickListener {
        showPrompt.set(!showPrompt.get())
    }
}