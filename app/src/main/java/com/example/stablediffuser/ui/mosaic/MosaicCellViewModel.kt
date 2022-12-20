package com.example.stablediffuser.ui.mosaic

import android.view.View
import com.example.stablediffuser.ui.art.ArtData

data class MosaicCellViewModel(
    val artData: ArtData,
    val onShowArt: View.OnClickListener
)