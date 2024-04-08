package com.myaxa.movies_data

import androidx.paging.PagingSource
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesPagingSourceFactory: MoviesPagingSource.Factory,
) {
    fun queryMovies(query: String): PagingSource<Int, Movie> {
        return moviesPagingSourceFactory.create(query)
    }

}