package com.myaxa.data.mappers

import com.myaxa.domain.movie_details.MovieDetails
import com.myaxa.movies.database.models.AgeRatingDBO
import com.myaxa.movies.database.models.CountryDBO
import com.myaxa.movies.database.models.GenreDBO
import com.myaxa.movies.database.models.MovieDBO
import com.myaxa.movies.database.models.MovieFullDBO
import com.myaxa.movies.database.models.NetworkDBO
import com.myaxa.movies.database.models.TypeDBO
import com.myaxa.movies_api.models.MovieDTO
import com.myaxa.movie_catalog_impl.Movie

/*
* Mapper for movie entities of movies-data and movie-details-data modules
* */
fun MovieDTO.toMovieFullDBO(): MovieFullDBO {

    val movieDBO = MovieDBO(
        id = id,
        name = name,
        year = year,
        description = description,
        rating = rating?.kp,
        poster = poster?.previewUrl,
        backdrop = backdrop?.url,
        isSeries = isSeries,
        typeId = 0,
        ageRatingId = null,
        networkId = null,
    )

    return MovieFullDBO(
        movie = movieDBO,

        type = TypeDBO(title = type),
        ageRating = ageRating?.let { AgeRatingDBO(title = "$it+") },
        network = networks?.let { NetworkDBO(title = it.items[0].name.toString()) },
        countries = countries.map { CountryDBO(title = it.name) },
        genres = genres.map { GenreDBO(title = it.name) },
    )
}

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

fun MovieDTO.toMovie(): Movie {
    return Movie(
        id, name, type, year, poster = poster?.url
    )
}