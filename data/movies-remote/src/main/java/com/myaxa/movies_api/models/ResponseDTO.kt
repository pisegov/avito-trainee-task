package com.myaxa.movies_api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDTO(
    @SerialName("docs") val movies: List<MovieDTO>,
    @SerialName("pages") val pages: Int,
)