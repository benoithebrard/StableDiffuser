package com.example.stablediffuser.ui.mosaic

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import com.example.stablediffuser.BR
import com.example.stablediffuser.R
import com.example.stablediffuser.data.lexica.LexicaImage
import me.tatarka.bindingcollectionadapter2.ItemBinding

class MosaicViewModel {
    val items: ObservableList<MosaicCellViewModel> = ObservableArrayList()

    val itemBinding = ItemBinding.of<MosaicCellViewModel>(BR.viewModel, R.layout.item_mosaic_cell)

    fun showContent(viewModels: List<MosaicCellViewModel>) {
        items.addAll(viewModels)
    }
}

internal fun List<LexicaImage>.toMosaicCellViewModels(
    onShowArt: (image: LexicaImage) -> Unit
): List<MosaicCellViewModel> = map { image ->
    with(image) {
        MosaicCellViewModel(
            imageUrl = srcSmall,
            hasWarning = nsfw,
            onShowArt = {
                onShowArt(image)
            }
        )
    }
}