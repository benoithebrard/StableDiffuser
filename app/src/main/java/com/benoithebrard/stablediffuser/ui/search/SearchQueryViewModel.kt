package com.benoithebrard.stablediffuser.ui.search

import android.view.View

data class SearchQueryViewModel(
    val query: String,
    val onLoadQuery: View.OnClickListener
)

internal fun List<String>.toQueryViewModels(
    onLoadQuery: (String) -> Unit
): List<SearchQueryViewModel> = map { query ->
    SearchQueryViewModel(
        query = query,
        onLoadQuery = {
            onLoadQuery(query)
        }
    )
}