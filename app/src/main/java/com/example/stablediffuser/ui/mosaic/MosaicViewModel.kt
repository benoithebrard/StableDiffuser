package com.example.stablediffuser.ui.mosaic

import androidx.recyclerview.widget.DiffUtil
import com.example.stablediffuser.BR
import com.example.stablediffuser.R
import com.example.stablediffuser.network.entities.LexicaImage
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList

class MosaicViewModel {

    private val cellDiffCallback: DiffUtil.ItemCallback<MosaicCellViewModel> =
        object : DiffUtil.ItemCallback<MosaicCellViewModel>() {
            override fun areItemsTheSame(
                oldItem: MosaicCellViewModel,
                newItem: MosaicCellViewModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MosaicCellViewModel,
                newItem: MosaicCellViewModel
            ): Boolean {
                return oldItem.imageUrl == newItem.imageUrl &&
                        oldItem.hasWarning == newItem.hasWarning
            }
        }

    val items: DiffObservableList<MosaicCellViewModel> = DiffObservableList(cellDiffCallback)

    val itemBinding = ItemBinding.of<MosaicCellViewModel>(BR.viewModel, R.layout.item_mosaic_cell)

    fun showContent(viewModels: List<MosaicCellViewModel>) {
        items.update(viewModels)
    }
}

internal fun List<LexicaImage>.toMosaicCellViewModels(
    onShowArt: (image: LexicaImage) -> Unit
): List<MosaicCellViewModel> = map { image ->
    with(image) {
        MosaicCellViewModel(
            id = id,
            imageUrl = srcSmall,
            hasWarning = nsfw,
            onShowArt = {
                onShowArt(image)
            }
        )
    }
}