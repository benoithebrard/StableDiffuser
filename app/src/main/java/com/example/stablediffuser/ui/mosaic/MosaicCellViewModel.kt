package com.example.stablediffuser.ui.mosaic

import android.view.View

data class MosaicCellViewModel(
    val imageUrl: String,
    val hasWarning: Boolean,
    val onShowArt: View.OnClickListener
)