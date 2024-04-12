package com.myaxa.data.movie_details

import com.myaxa.domain.movie_details.MovieDetails
import com.myaxa.movies.database.models.MovieDBO

fun MovieDBO.toMovieDetails() = MovieDetails(
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