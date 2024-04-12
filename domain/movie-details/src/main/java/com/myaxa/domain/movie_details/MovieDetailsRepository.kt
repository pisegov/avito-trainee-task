package com.myaxa.domain.movie_details

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {
    fun getMovieDetailsFlow(id: Long): Flow<MovieDetails?>

    suspend fun loadMovieDetails(id: Long)

    fun <T: DetailsInfoModel> getInfo(movieId: Long, type: Class<T>): PagingSource<Int, T>
}