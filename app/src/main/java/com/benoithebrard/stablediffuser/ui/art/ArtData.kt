package com.benoithebrard.stablediffuser.ui.art

import com.benoithebrard.stablediffuser.network.lexica.LexicaImage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val MIN_PIXEL_COUNT_HD = 700

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

    @SerialName("hd")
    val hd: Boolean = false,
)

internal fun LexicaImage.toArtData(): ArtData = ArtData(
    id = id,
    url = src,
    thumbUrl = srcSmall,
    prompt = prompt,
    dimensions = "${width}x${height}",
    nsfw = nsfw,
    hd = width > MIN_PIXEL_COUNT_HD && height > MIN_PIXEL_COUNT_HD
)