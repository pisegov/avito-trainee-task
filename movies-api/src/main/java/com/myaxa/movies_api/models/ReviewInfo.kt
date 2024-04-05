package com.myaxa.movies_api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewInfo(
    @SerialName("count") var count: Int? = null,
    @SerialName("positiveCount") var positiveCount: Int? = null,
    @SerialName("percentage") var percentage: String? = null,
)