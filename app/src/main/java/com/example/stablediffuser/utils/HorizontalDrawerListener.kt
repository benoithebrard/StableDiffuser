package com.example.stablediffuser.utils

import android.view.View
import androidx.drawerlayout.widget.DrawerLayout

class HorizontalDrawerListener(
    private val contentView: View
) : DrawerLayout.DrawerListener {

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        val translationX = drawerView.width * slideOffset
        contentView.translationX = translationX
    }

    override fun onDrawerOpened(drawerView: View) {
    }

    override fun onDrawerClosed(drawerView: View) {
    }

    override fun onDrawerStateChanged(newState: Int) {
    }
}