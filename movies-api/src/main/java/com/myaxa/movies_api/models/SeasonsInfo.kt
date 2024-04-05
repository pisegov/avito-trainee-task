package com.myaxa.movies_api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeasonsInfo(
    @SerialName("number") var number: Int? = null,
    @SerialName("episodesCount") var episodesCount: Int? = null,
)