package com.myaxa.movies.database.dao

import com.myaxa.movies.database.models.MovieDetailsInfoDBO

interface GenericDetailsDao<T: MovieDetailsInfoDBO> {

    suspend fun insertList(list: @JvmSuppressWildcards List<T>, movieId: Long)

    suspend fun getList(movieId: Long, offset: Int, rowCount: Int): List<MovieDetailsInfoDBO>

    suspend fun getItemsCount(movieId: Long): Int
}