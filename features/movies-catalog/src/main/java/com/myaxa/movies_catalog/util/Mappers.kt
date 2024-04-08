package com.myaxa.movies_catalog.util

import com.myaxa.movies_catalog.models.MovieInCatalog
import com.myaxa.movies_data.Movie

fun Movie.toMovieUI() = MovieInCatalog(
    id = id,
    name = name,
    type = type,
    typeNumber = typeNumber,
    year = year,
    rating = rating,
    votes = votes,
    ageRating = ageRating,
    logoUrl = logoUrl,
    poster = poster,
    backdrop = backdrop,
    reviewCount = reviewCount,
    isSeries = isSeries,
)