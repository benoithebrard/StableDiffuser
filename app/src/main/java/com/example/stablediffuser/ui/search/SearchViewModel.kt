package com.example.stablediffuser.ui.search

import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableList
import com.example.stablediffuser.BR
import com.example.stablediffuser.R
import me.tatarka.bindingcollectionadapter2.ItemBinding

data class SearchViewModel(
    val onShowMosaic: View.OnClickListener,
    val onClearSearch: View.OnClickListener,
    val onClearQueries: View.OnClickListener,
    val onLoadQuery: (String) -> Unit,
) {
    val isSearchEnabled = ObservableBoolean()

    val isClearVisible = ObservableBoolean()

    val items: ObservableList<SearchQueryViewModel> = ObservableArrayList()

    val itemBinding = ItemBinding.of<SearchQueryViewModel>(BR.viewModel, R.layout.item_search_query)

    fun setQueries(queries: List<String>) {
        items.clear()
        queries.toQueryViewModels(onLoadQuery).also { viewModels ->
            items.addAll(viewModels)
        }
    }
}