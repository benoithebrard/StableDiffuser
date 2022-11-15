package com.example.stablediffuser.ui.mosaic

import android.view.View

data class MosaicViewModel(
    val title: String,
    val onShowArt: View.OnClickListener,
    val onShowSearch: View.OnClickListener
)