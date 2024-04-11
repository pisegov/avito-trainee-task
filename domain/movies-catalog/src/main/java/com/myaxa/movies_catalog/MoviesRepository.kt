package com.myaxa.movies_catalog

import androidx.paging.PagingSource

interface MoviesRepository {
    fun queryMovies(query: String, filters: Filters?): PagingSource<Int, Movie>

    suspend fun getFilters(): Filters
}
