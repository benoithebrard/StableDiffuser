package com.example.stablediffuser.utils.extensions

import com.example.stablediffuser.ui.art.ArtData

internal fun List<ArtData>.containsArt(artData: ArtData) = any { art ->
    art.id == artData.id
}