package com.example.stablediffuser.ui.search

import android.view.View

data class SearchQueryViewModel(
    val query: String,
    val onLoadQuery: View.OnClickListener
)