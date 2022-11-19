package com.example.stablediffuser.ui.art

import android.view.View

data class ArtViewModel(
    val title: String,
    val imageUrl: String,
    val onShowMosaic: View.OnClickListener,
    val onShowSearch: View.OnClickListener
)