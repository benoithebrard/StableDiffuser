package com.benoithebrard.stablediffuser.network.lexica

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LexicaSearchResponse(

    @SerialName("images")
    val images: List<LexicaImage>
)