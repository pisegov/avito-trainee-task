package com.myaxa.data.movie_details_remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDTO (
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("type") val type: String,
    @SerialName("typeNumber") val typeNumber: Int,
    @SerialName("year") val year: Int,
    @SerialName("description") val description: String? = null,
    @SerialName("shortDescription") val shortDescription: String? = null,
    @SerialName("isSeries") val isSeries: Boolean,
)
