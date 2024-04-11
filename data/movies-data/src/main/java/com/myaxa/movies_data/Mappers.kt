package com.myaxa.movies_data

import com.myaxa.movies.database.models.MovieDBO
import com.myaxa.movies_api.models.MovieDTO
import com.myaxa.movies_catalog.Movie

fun MovieDBO.toMovie() = Movie(
    id = id,
    name = name,
    type = type,
    year = year,
    rating = rating,
    reviewCount = reviewCount,
    ageRating = ageRating,
    poster = poster,
    /*
    * // genres
    * description
    * short description
    *
    * reviews
    * images/posters
    * actors
    * seasons/episodes
    *
    * */
)