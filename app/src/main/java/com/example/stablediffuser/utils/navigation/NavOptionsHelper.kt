package com.example.stablediffuser.utils.navigation

import androidx.navigation.navOptions
import com.example.stablediffuser.R

object NavOptionsHelper {

    val defaultScreenNavOptions = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    val popToSearchNavOptions = navOptions {
        anim {
            enter = R.anim.fade_in
            exit = R.anim.fade_out
            popExit = R.anim.fade_out
            popEnter = R.anim.fade_in
        }
        popUpTo(R.id.search_dest) {
            inclusive = true
        }
    }
}