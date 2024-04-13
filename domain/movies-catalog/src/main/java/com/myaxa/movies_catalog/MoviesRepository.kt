package com.myaxa.movies_catalog

import androidx.paging.PagingSource
import com.myaxa.movies_catalog.filters.Filters

interface MoviesRepository {
    fun queryMovies(query: String, filters: Filters?): PagingSource<Int, Movie>

    suspend fun getFilters(): Filters
}
