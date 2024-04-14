package com.myaxa.movie.details.models

import com.myaxa.domain.movie_details.MovieDetails

internal data class MovieDetailsUI(
    val id: Long,
    val name: String?,
    val year: Int?,
    val isSeries: Boolean,
    val rating: Double? = null,
    val reviewCount: Int? = null,
    val ageRating: Int? = null,
    val poster: String? = null,
    val backdrop: String? = null,
    val description: String? = null,
    val shortDescription: String? = null,
)

internal fun MovieDetails.toMovieDetailsUI(): MovieDetailsUI =
    MovieDetailsUI(
        id = id,
        name = name,
        year = year,
        rating = rating,
        reviewCount = reviewCount,
        ageRating = ageRating,
        poster = poster,
        backdrop = backdrop,
        description = description,
        isSeries = isSeries,
    )