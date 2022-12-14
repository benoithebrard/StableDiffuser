package com.benoithebrard.stablediffuser.utils

import androidx.navigation.navOptions
import com.benoithebrard.stablediffuser.R

object NavOptionsHelper {

    val slidingNavOptions = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    val fadingNavOptions = navOptions {
        anim {
            enter = R.anim.fade_in
            exit = R.anim.fade_out
            popExit = R.anim.fade_out
            popEnter = R.anim.fade_in
        }
    }
}