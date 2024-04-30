package com.myaxa.movies_api

import com.myaxa.movies_api.models.FilterOptionDTO
import com.myaxa.movies_api.models.MovieDTO
import com.myaxa.movies_api.models.NetworkRequestFilters
import com.myaxa.movies_api.models.ResponseDTO
import com.myaxa.network.RetrofitModule
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.create

class MoviesRemoteDataSource @Inject internal constructor(
    private val moviesApi: MoviesApi,
) {
    suspend fun getMovies(
        query: String,
        filters: NetworkRequestFilters,
        page: Int,
        pageSize: Int,
    ): Result<ResponseDTO> = withContext(Dispatchers.IO) {

        if (query.isEmpty()) return@withContext getMoviesWithEmptyQuery(page, pageSize, filters)

        val responseResult = moviesApi.search(query, page, pageSize)

        val queriedList = responseResult.getOrNull()?.movies
            ?: return@withContext responseResult

        val filteredResponseResult = filterMoviesList(
            ids = queriedList.map { it.id },
            filters
        )

        val filteredList = filteredResponseResult.getOrNull()?.movies
            ?: return@withContext filteredResponseResult

        Result.success(
            ResponseDTO(movies = filteredList, pages = responseResult.getOrNull()?.pages ?: 1)
        )
    }

    private suspend fun getMoviesWithEmptyQuery(
        page: Int,
        pageSize: Int,
        filters: NetworkRequestFilters,
    ): Result<ResponseDTO> = withContext(Dispatchers.IO) {
        moviesApi.emptyQueryMovies(
            page, pageSize,
            year = filters.year?.let { listOf(it) },
            rating = filters.rating?.let { listOf("$it-10") },
            countries = filters.countries,
            types = filters.types,
            networks = filters.networks,
            genres = filters.genres,
            ageRatings = filters.ageRatings,
        )
    }
    private suspend fun filterMoviesList(
        ids: List<Long>,
        filters: NetworkRequestFilters,
    ): Result<ResponseDTO> = withContext(Dispatchers.IO) {
        moviesApi.filter(
            ids = ids,
            year = filters.year?.let { listOf(it) },
            rating = filters.rating?.let { listOf("$it-10") },
            countries = filters.countries,
            types = filters.types,
            networks = filters.networks,
            genres = filters.genres,
            ageRatings = filters.ageRatings,
        )
    }

    suspend fun getFilterOptions(
        filter: String,
    ): Result<List<FilterOptionDTO>> {
        return moviesApi.getFilterOptions(filter)
    }

    suspend fun getMovieById(id: Long): Result<MovieDTO> = withContext(Dispatchers.IO) {
        moviesApi.movie(id)
    }
}

fun MoviesRemoteDataSource(retrofitModule: RetrofitModule): MoviesRemoteDataSource {
    val api: MoviesApi = retrofitModule.retrofit.create()

    return MoviesRemoteDataSource(api)
}