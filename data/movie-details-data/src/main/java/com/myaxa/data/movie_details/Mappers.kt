package com.myaxa.data.movie_details

import com.myaxa.domain.movie_details.MovieDetails
import com.myaxa.movies.database.models.MovieDBO

fun MovieDBO.toMovieDetails() = MovieDetails(
    id = id,
    name = name,
    year = year,
    rating = rating,
    poster = poster,
    backdrop = backdrop,
    description = description,
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