package com.example.stablediffuser.ui.art

import android.view.View

data class ArtViewModel(
    val imageUrl: String,
    val onShowMosaic: View.OnClickListener,
)