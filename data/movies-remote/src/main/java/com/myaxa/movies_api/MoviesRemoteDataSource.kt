package com.myaxa.movies_api

import com.myaxa.movies_api.models.FilterOptionDTO
import com.myaxa.movies_api.models.MovieDTO
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
        countries: List<String>? = null,
    ): Result<ResponseDTO> = withContext(Dispatchers.IO) {
        moviesApi.emptyQueryMovies(
            page, pageSize,
            year = year?.let { listOf(it) },
            rating = rating?.let { listOf("$it-10") },
            countries = countries,
        )
    }
    suspend fun filterMoviesList(
        ids: List<Long>,
        year: String? = null,
        rating: String? = null,
        countries: List<String>? = null,
    ): Result<ResponseDTO> = withContext(Dispatchers.IO) {
        moviesApi.filter(
            ids = ids,
            year = year?.let { listOf(it) },
            rating = rating?.let { listOf("$it-10") },
            countries = countries,
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