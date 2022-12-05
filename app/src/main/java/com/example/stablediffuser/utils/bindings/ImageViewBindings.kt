package com.example.stablediffuser.utils.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter(value = ["android:src", "thumbSrc"], requireAll = false)
fun ImageView.setImageUrl(imageUrl: String?, thumbSrc: String?) {
    if (imageUrl != null) {
        val requestBuilder = Glide.with(this).load(imageUrl)
        if (thumbSrc != null) {
            requestBuilder.thumbnail(
                Glide.with(this)
                    .load(thumbSrc)
                    .fitCenter()
            ).into(this)
        } else {
            requestBuilder.into(this)
        }
    }
}