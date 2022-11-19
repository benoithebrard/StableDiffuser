package com.example.stablediffuser.utils.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("spanCount")
fun RecyclerView.setSpanCount(spanCount: Int?) {
    if (spanCount != null && layoutManager !is GridLayoutManager) {
        layoutManager = GridLayoutManager(context, spanCount)
    }
}