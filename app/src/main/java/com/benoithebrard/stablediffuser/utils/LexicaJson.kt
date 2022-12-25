package com.benoithebrard.stablediffuser.utils

import kotlinx.serialization.json.Json

object LexicaJson {

    internal val json: Json by lazy {
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }
}