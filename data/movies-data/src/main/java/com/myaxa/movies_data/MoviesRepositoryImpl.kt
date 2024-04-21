package com.myaxa.movies_data

import androidx.paging.PagingSource
import com.myaxa.movies_api.MoviesRemoteDataSource
import com.myaxa.movie_catalog_impl.Movie
import com.myaxa.movie_catalog_impl.MoviesRepository
import com.myaxa.movie_catalog_impl.filters.Filter.ListFilter
import com.myaxa.movie_catalog_impl.filters.FilterValue
import com.myaxa.movie_catalog_impl.filters.Filters
import com.myaxa.movie_catalog_impl.filters.contentTypeNames
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class MoviesRepositoryImpl @Inject internal constructor(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val moviesPagingSourceFactory: MoviesPagingSource.Factory,
) : MoviesRepository {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    override fun queryMovies(query: String, filters: Filters?): PagingSource<Int, Movie> {
        return moviesPagingSourceFactory.create(query, filters)
    }

    override suspend fun getFilters(): Filters {

        val countriesDeferred = scope.async { loadFilterOptions("countries.name") }
        val genresDeferred = scope.async { loadFilterOptions("genres.name") }
        val contentTypesDeferred = scope.async { loadFilterOptions("type") }

        val countries = countriesDeferred.await().takeIf { it.isNotEmpty() } ?: defaultCountriesList
        val genres = genresDeferred.await().takeIf { it.isNotEmpty() } ?: defaultGenresList
        val contentTypes = contentTypesDeferred.await().takeIf { it.isNotEmpty() } ?: defaultContentTypesList

        return Filters(
            countries = ListFilter(
                "Страны производства", countries
                .associateWith { FilterValue(it, false) }),
            genres = ListFilter(
                "Жанры", genres
                .associateWith { FilterValue(it, false) }),
            types = ListFilter(
                "Тип контента", contentTypes
                .associateWith { FilterValue(contentTypeNames[it] ?: it, false) }),
        )
    }

    private suspend fun loadFilterOptions(filter: String): List<String> {
        val result = remoteDataSource
            .getFilterOptions(filter)
        return result
            .getOrDefault(emptyList())
            .map { it.name }
    }
}

val defaultCountriesList = listOf("Россия", "США", "Великобритания", "Франция", "СССР", "Германия", "Италия")
val defaultGenresList = listOf("драма", "комедия", "биография", "криминал", "боевик", "триллер", "семейный", "фэнтези", "приклюения")
val defaultContentTypesList = listOf("movie", "cartoon", "tv-series", "animated-series", "anime")
