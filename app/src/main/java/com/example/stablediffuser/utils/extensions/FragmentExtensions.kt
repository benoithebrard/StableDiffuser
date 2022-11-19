package com.example.stablediffuser.utils.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

internal fun Fragment.setToolbarTitle(title: String) {
    (requireActivity() as AppCompatActivity).supportActionBar?.title = title.toTitle()
}