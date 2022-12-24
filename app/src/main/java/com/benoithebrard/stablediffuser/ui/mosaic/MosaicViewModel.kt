package com.benoithebrard.stablediffuser.ui.mosaic

import androidx.recyclerview.widget.DiffUtil
import com.benoithebrard.stablediffuser.BR
import com.benoithebrard.stablediffuser.R
import com.benoithebrard.stablediffuser.ui.art.ArtData
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList

class MosaicViewModel {

    private val cellDiffCallback: DiffUtil.ItemCallback<MosaicCellViewModel> =
        object : DiffUtil.ItemCallback<MosaicCellViewModel>() {
            override fun areItemsTheSame(
                oldItem: MosaicCellViewModel,
                newItem: MosaicCellViewModel
            ): Boolean {
                return oldItem.artData.id == newItem.artData.id
            }

            override fun areContentsTheSame(
                oldItem: MosaicCellViewModel,
                newItem: MosaicCellViewModel
            ): Boolean {
                return oldItem.artData == newItem.artData
            }
        }

    val items: DiffObservableList<MosaicCellViewModel> = DiffObservableList(cellDiffCallback)

    val itemBinding = ItemBinding.of<MosaicCellViewModel>(BR.viewModel, R.layout.item_mosaic_cell)

    fun showContent(viewModels: List<MosaicCellViewModel>) {
        items.update(viewModels)
    }
}

internal fun List<ArtData>.toMosaicCellViewModels(
    onShowArt: (artData: ArtData) -> Unit
): List<MosaicCellViewModel> = map { artData ->
    MosaicCellViewModel(
        artData = artData,
        onShowArt = {
            onShowArt(artData)
        }
    )
}