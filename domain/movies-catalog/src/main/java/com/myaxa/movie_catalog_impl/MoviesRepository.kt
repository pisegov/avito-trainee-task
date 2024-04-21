package com.myaxa.movie_catalog_impl

import androidx.paging.PagingSource
import com.myaxa.movie_catalog_impl.filters.Filters

interface MoviesRepository {
    fun queryMovies(query: String, filters: Filters?): PagingSource<Int, Movie>

    suspend fun getFilters(): Filters
}
