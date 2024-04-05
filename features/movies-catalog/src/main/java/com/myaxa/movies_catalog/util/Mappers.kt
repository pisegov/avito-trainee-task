package com.myaxa.movies_catalog.util

import com.myaxa.movies_catalog.ScreenState
import com.myaxa.movies_catalog.models.MovieInCatalog
import com.myaxa.movies_data.LoadingState
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

fun LoadingState.toScreenState() = when (this) {
    is LoadingState.Success -> {
        val moviesList = data.map {
            it.toMovieUI()
        }
        ScreenState.Success(moviesList)
    }

    is LoadingState.NoData -> {
        ScreenState.NoDataError
    }

    is LoadingState.NetworkError -> {
        ScreenState.NetworkError
    }

    is LoadingState.Started -> {
        ScreenState.Loading
    }

    else -> ScreenState.None
}