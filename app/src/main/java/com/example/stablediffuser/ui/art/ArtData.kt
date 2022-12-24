package com.example.stablediffuser.ui.art

import com.example.stablediffuser.network.lexica.LexicaImage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtData(
    @SerialName("id")
    val id: String,

    @SerialName("url")
    val url: String,

    @SerialName("thumbUrl")
    val thumbUrl: String,

    @SerialName("prompt")
    val prompt: String,

    @SerialName("dimensions")
    val dimensions: String,

    @SerialName("nsfw")
    val nsfw: Boolean,
)

internal fun LexicaImage.toArtData(): ArtData = ArtData(
    id = id,
    url = src,
    thumbUrl = srcSmall,
    prompt = prompt,
    nsfw = nsfw,
    dimensions = "${width}x${height}"
)