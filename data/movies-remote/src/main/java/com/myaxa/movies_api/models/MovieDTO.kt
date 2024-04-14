package com.myaxa.movies_api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDTO(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String?,
    @SerialName("type") val type: String,
    @SerialName("year") val year: Int?,
    @SerialName("rating") val rating: Rating? = Rating(),
    @SerialName("reviewInfo") val reviewInfo: ReviewInfo? = ReviewInfo(),
    @SerialName("ageRating") val ageRating: Int? = null,
    @SerialName("poster") val poster: Poster? = Poster(),
    @SerialName("backdrop") val backdrop: Poster? = Poster(),
    @SerialName("description") val description: String? = null,
    @SerialName("shortDescription") val shortDescription: String? = null,
    @SerialName("isSeries") val isSeries: Boolean,
    @SerialName("genres") val genres: List<Genres> = listOf(),
    @SerialName("countries") val countries: List<Country> = listOf(),
    @SerialName("networks") val networks: Networks? = null,
)