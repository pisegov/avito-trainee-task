package com.myaxa.domain.movie_details

import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {
    val movieDetailsFlow: Flow<MovieDetails>

    suspend fun loadMovieDetails()
}