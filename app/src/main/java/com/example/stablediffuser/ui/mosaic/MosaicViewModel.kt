package com.example.stablediffuser.ui.mosaic

import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import com.example.stablediffuser.BR
import com.example.stablediffuser.R
import me.tatarka.bindingcollectionadapter2.ItemBinding

data class MosaicViewModel(
    val title: String,
    val onShowArt: View.OnClickListener,
    val onShowSearch: View.OnClickListener
) {
    val items: ObservableList<MosaicCellViewModel> = ObservableArrayList()

    val itemBinding = ItemBinding.of<MosaicCellViewModel>(BR.viewModel, R.layout.item_mosaic_cell)

    fun setImageUrls(imageUrls: List<String>) {
        imageUrls.map { url ->
            MosaicCellViewModel(
                imageUrl = url,
                onShowArt = onShowArt
            )
        }.also { viewModels ->
            items.addAll(viewModels)
        }
    }
}