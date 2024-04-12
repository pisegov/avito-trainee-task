package com.myaxa.domain.movie_details

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {
    fun getMovieDetailsFlow(id: Long): Flow<MovieDetails?>

    fun getActors(movieId: Long): PagingSource<Int, Actor>

    suspend fun loadMovieDetails(id: Long)
}