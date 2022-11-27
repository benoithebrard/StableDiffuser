package com.example.stablediffuser.ui.search

import android.view.View
import androidx.databinding.ObservableBoolean

data class SearchViewModel(
    val onShowMosaic: View.OnClickListener,
) {
    val isSearchEnabled = ObservableBoolean()
}