package com.benoithebrard.stablediffuser.utils.bindings

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("app:visible")
fun View.setIsVisible(isVisible: Boolean?) {
    if (isVisible != null) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}

@BindingAdapter("app:selected")
fun View.setIsSelected(isSelected: Boolean?) {
    if (isSelected != null) {
        this.isSelected = isSelected
    }
}