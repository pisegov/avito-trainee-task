package com.myaxa.movies_catalog.util

import com.myaxa.movies_catalog.models.MovieInCatalog
import com.myaxa.movies_catalog.Movie

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