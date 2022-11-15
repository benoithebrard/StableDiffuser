package com.example.stablediffuser.utils.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("android:src")
fun ImageView.setImageUrl(imageUrl: String?) {
    if (imageUrl != null) {
        Glide.with(this).load(imageUrl).into(this)
    }
}