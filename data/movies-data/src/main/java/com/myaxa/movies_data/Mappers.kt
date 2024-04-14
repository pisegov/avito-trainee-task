package com.myaxa.movies_data

import com.myaxa.movies_api.models.MovieDTO
import com.myaxa.movies_catalog.Movie

fun MovieDTO.toMovie(): Movie {
    return Movie(
        id, name, type, year, poster = poster?.url
    )
}