package com.myaxa.movies_api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDTO(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("type") val type: String,
    @SerialName("typeNumber") val typeNumber: Int,
    @SerialName("year") val year: Int,
    @SerialName("description") val description: String? = null,
    @SerialName("shortDescription") val shortDescription: String? = null,
    @SerialName("slogan") val slogan: String? = null,
    @SerialName("rating") val rating: Rating? = Rating(),
    @SerialName("votes") val votes: Votes? = Votes(),
    @SerialName("ageRating") val ageRating: Int? = null,
    @SerialName("logo") val logo: Logo? = Logo(),
    @SerialName("poster") val poster: Poster? = Poster(),
    @SerialName("backdrop") val backdrop: Backdrop? = Backdrop(),
    @SerialName("genres") val genres: List<Genres> = listOf(),
    @SerialName("persons") val persons: List<Persons> = listOf(),
    @SerialName("reviewInfo") val reviewInfo: ReviewInfo? = ReviewInfo(),
    @SerialName("seasonsInfo") val seasonsInfo: List<SeasonsInfo> = listOf(),
    @SerialName("totalSeriesLength") val totalSeriesLength: Int? = null,
    @SerialName("seriesLength") val seriesLength: Int? = null,
    @SerialName("isSeries") val isSeries: Boolean,
    @SerialName("networks") val networks: Networks? = Networks(),
)