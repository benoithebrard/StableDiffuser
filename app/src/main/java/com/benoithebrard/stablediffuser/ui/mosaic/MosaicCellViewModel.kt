package com.benoithebrard.stablediffuser.ui.mosaic

import android.view.View
import com.benoithebrard.stablediffuser.ui.art.ArtData

data class MosaicCellViewModel(
    val artData: ArtData,
    val onShowArt: View.OnClickListener
)