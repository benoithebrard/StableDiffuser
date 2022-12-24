package com.benoithebrard.stablediffuser.network.lexica

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LexicaImage(

    // The ID of the image
    @SerialName("id")
    val id: String,

    // URL for the image's gallery
    @SerialName("gallery")
    val gallery: String,

    // Link to this image
    @SerialName("src")
    val src: String,

    // Link to an compressed & optimized version of this image
    @SerialName("srcSmall")
    val srcSmall: String,

    // The prompt used to generate this image
    @SerialName("prompt")
    val prompt: String,

    // Image dimensions
    @SerialName("width")
    val width: Int,

    @SerialName("height")
    val height: Int,

    @SerialName("seed")
    val seed: String,

    // Whether this image is a grid of multiple images
    @SerialName("grid")
    val grid: Boolean,

    // The model used to generate this image
    @SerialName("model")
    val model: ModelType = ModelType.Unknown,

    // Guidance scale
    @SerialName("guidance")
    val guidance: Int,

    // The ID for this image's prompt
    @SerialName("promptid")
    val promptId: String,

    // Whether this image is classified as NSFW
    @SerialName("nsfw")
    val nsfw: Boolean,
)

@Serializable
enum class ModelType {

    @SerialName("stable-diffusion")
    StableDiffusion,

    Unknown
}