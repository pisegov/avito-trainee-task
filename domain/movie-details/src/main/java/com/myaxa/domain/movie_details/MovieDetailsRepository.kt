package com.myaxa.domain.movie_details

import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {
    fun getMovieDetailsFlow(id: Long): Flow<MovieDetails?>

    suspend fun loadMovieDetails(id: Long)
}