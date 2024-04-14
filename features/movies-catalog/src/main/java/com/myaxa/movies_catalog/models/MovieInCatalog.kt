package com.myaxa.movies_catalog.models

import com.myaxa.movies_catalog.Movie

data class MovieInCatalog(
    val id: Long,
    val name: String?,
    val type: String,
    val year: Int?,
    val rating: Double? = null,
    val reviewCount: Int? = null,
    val ageRating: Int? = null,
    val poster: String? = null,
)

fun Movie.toMovieUI() = MovieInCatalog(
    id = id,
    name = name,
    type = type,
    year = year,
    rating = rating,
    ageRating = ageRating,
    poster = poster,
    reviewCount = reviewCount,
)

