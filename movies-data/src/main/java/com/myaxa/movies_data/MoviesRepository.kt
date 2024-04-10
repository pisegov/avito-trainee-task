package com.myaxa.movies_data

import androidx.paging.PagingSource
import com.myaxa.movies.database.MoviesLocalDataSource
import com.myaxa.movies_api.MoviesRemoteDataSource
import com.myaxa.movies_data.Filter.ListFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MoviesLocalDataSource,
    private val moviesPagingSourceFactory: MoviesPagingSource.Factory,
) {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    fun queryMovies(query: String, filters: Filters?): PagingSource<Int, Movie> {
        return moviesPagingSourceFactory.create(query, filters)
    }

    suspend fun getFilters(): Filters {

        val countries = scope.async { loadFilterOptions("countries.name") }
        val genres = scope.async { loadFilterOptions("genres.name") }
        val contentTypes = scope.async { loadFilterOptions("type") }

        return Filters(
            countries = ListFilter("Страны производства", countries.await().associateWith { false }),
            genres = ListFilter("Жанры", genres.await().associateWith { false }),
            types = ListFilter("Тип контента", contentTypes.await().associateWith { false }),
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