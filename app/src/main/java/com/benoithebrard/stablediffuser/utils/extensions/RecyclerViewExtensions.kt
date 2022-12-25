package com.benoithebrard.stablediffuser.utils.extensions

import android.content.Context
import android.view.Surface
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

internal fun RecyclerView.setupAutoOrientationGrid() {
    val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
    val columnCount: Int = when (display.rotation) {
        Surface.ROTATION_90, Surface.ROTATION_270 -> 6
        else -> 3
    }

    layoutManager = GridLayoutManager(context, columnCount)
    adapter?.stateRestorationPolicy =
        RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
}