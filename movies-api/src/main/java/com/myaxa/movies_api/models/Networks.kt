package com.myaxa.movies_api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Networks(
    @SerialName("items") var items: List<Items> = listOf(),
)