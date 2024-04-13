package com.myaxa.movies_data

import androidx.paging.PagingSource
import com.myaxa.movies.database.datasources.MoviesLocalDataSource
import com.myaxa.movies_api.MoviesRemoteDataSource
import com.myaxa.movies_catalog.filters.Filter.ListFilter
import com.myaxa.movies_catalog.filters.FilterValue
import com.myaxa.movies_catalog.Movie
import com.myaxa.movies_catalog.MoviesRepository
import com.myaxa.movies_catalog.filters.Filters
import com.myaxa.movies_catalog.filters.contentTypeNames
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MoviesLocalDataSource,
    private val moviesPagingSourceFactory: MoviesPagingSource.Factory,
) : MoviesRepository {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    override fun queryMovies(query: String, filters: Filters?): PagingSource<Int, Movie> {
        return moviesPagingSourceFactory.create(query, filters)
    }

    override suspend fun getFilters(): Filters {

        val countries = scope.async { loadFilterOptions("countries.name") }
        val genres = scope.async { loadFilterOptions("genres.name") }
        val contentTypes = scope.async { loadFilterOptions("type") }

        return Filters(
            countries = ListFilter("Страны производства", countries.await()
                .associateWith { FilterValue(it, false) }),
            genres = ListFilter("Жанры", genres.await()
                .associateWith { FilterValue(it, false) }),
            types = ListFilter("Тип контента", contentTypes.await()
                .associateWith { FilterValue(contentTypeNames[it] ?: it, false) }),
        )
    }

    private suspend fun loadFilterOptions(filter: String): List<String> {
        val result = remoteDataSource
            .getFilterOptions(filter)
        val list = result
            .getOrDefault(emptyList())
            .map { it.name }
        // localDataSource.insertFilterOptions(filter, list)
        // return loadDataSource.getFilterOptions(filter)
        return list
    }
}