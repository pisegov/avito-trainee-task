package com.myaxa.movies_api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Poster(
    @SerialName("url") val url: String? = null,
    @SerialName("previewUrl") val previewUrl: String? = null,
)