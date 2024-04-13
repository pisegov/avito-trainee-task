package com.myaxa.movie.details.models

import com.myaxa.domain.movie_details.MovieDetails

internal data class MovieDetailsUI(
    val id: Long,
    val name: String?,
    val type: String,
    val year: Int?,
    val rating: Double? = null,
    val reviewCount: Int? = null,
    val ageRating: Int? = null,
    val poster: String? = null,
    val backdrop: String? = null,
    val description: String? = null,
    val shortDescription: String? = null,
    val isSeries: Boolean,
    //  val genres: List<String> = listOf(),
    //  val networks: List<String> = listOf(),
    //  val countries: List<String> = listOf(),
    //  val persons: List<String> = listOf(),
)

internal fun MovieDetails.toMovieDetailsUI(): MovieDetailsUI =
    MovieDetailsUI(
        id = id,
        name = name,
        type = type,
        year = year,
        rating = rating,
        reviewCount = reviewCount,
        ageRating = ageRating,
        poster = poster,
        backdrop = backdrop,
        description = description,
        shortDescription = shortDescription,
        isSeries = isSeries,
        /*
        * // genres
        *
        * reviews
        * images/posters
        * actors
        * seasons/episodes
        *
        * */
    )