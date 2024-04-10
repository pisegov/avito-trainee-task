package com.myaxa.movies_api

import com.myaxa.movies_api.models.FilterOptionDTO
import com.myaxa.movies_api.models.ResponseDTO
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.create

class MoviesRemoteDataSource @Inject internal constructor(
    private val moviesApi: MoviesApi,
) {
    suspend fun getMovies(
        query: String,
        page: Int,
        pageSize: Int,
    ): Result<ResponseDTO> = withContext(Dispatchers.IO) {
        moviesApi.search(query, page, pageSize)
    }

    suspend fun getMovies(
        page: Int,
        pageSize: Int,
        year: String? = null,
        rating: String? = null,
    ): Result<ResponseDTO> = withContext(Dispatchers.IO) {
        moviesApi.emptyQueryMovies(
            page, pageSize,
            year = year?.let { listOf(it) },
            rating = rating?.let { listOf("$it-10") },
        )
    }
    suspend fun filterMoviesList(
        ids: List<Long>,
        year: String? = null,
        rating: String? = null,
    ): Result<ResponseDTO> = withContext(Dispatchers.IO) {
        moviesApi.filter(
            ids = ids,
            year = year?.let { listOf(it) },
            rating = rating?.let { listOf("$it-10") }
        )
    }

    suspend fun getFilterOptions(
        filter: String,
    ): Result<List<FilterOptionDTO>> {
        return moviesApi.getFilterOptions(filter)
    }
}

fun MoviesRemoteDataSource(
    baseUrl: String,
    apiKey: String,
): MoviesRemoteDataSource {
    val api: MoviesApi = retrofit(baseUrl, apiKey).create()

    return MoviesRemoteDataSource(api)
}