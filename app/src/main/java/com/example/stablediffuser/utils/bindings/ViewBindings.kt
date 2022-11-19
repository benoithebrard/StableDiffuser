package com.example.stablediffuser.utils.bindings

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("app:visible")
fun View.setIsVisible(isVisible: Boolean?) {
    if (isVisible != null) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}