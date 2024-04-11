package com.myaxa.movies_api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilterOptionDTO(
    @SerialName("name") val name: String,
)
