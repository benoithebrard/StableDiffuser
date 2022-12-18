package com.example.stablediffuser.network.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LexicaSearchResponse(

    @SerialName("images")
    val images: List<LexicaImage>
)