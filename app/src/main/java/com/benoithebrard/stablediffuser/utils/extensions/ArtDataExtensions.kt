package com.benoithebrard.stablediffuser.utils.extensions

import com.benoithebrard.stablediffuser.ui.art.ArtData

internal fun List<ArtData>.containsArt(artData: ArtData) = any { art ->
    art.id == artData.id
}