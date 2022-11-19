package com.example.stablediffuser.utils.extensions

import java.util.*

internal fun String.toTitle(): String = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(
        Locale.ROOT
    ) else it.toString()
}