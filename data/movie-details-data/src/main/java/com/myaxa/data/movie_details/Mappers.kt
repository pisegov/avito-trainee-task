package com.myaxa.data.movie_details

import com.myaxa.domain.movie_details.MovieDetails
import com.myaxa.movies.database.models.MovieDBO
import com.myaxa.movies.database.models.MovieFullDBO

fun MovieFullDBO.toMovieDetails() = MovieDetails(
    id = movie.id,
    name = movie.name,
    year = movie.year,
    rating = movie.rating,
    poster = movie.poster,
    backdrop = movie.backdrop,
    description = movie.description,
    isSeries = movie.isSeries,
    genres = genres.joinToString { it.title }.replaceFirstChar { it.uppercaseChar() }
)