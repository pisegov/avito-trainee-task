package com.myaxa.movies_api

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
}

fun MoviesRemoteDataSource(
    baseUrl: String,
    apiKey: String,
): MoviesRemoteDataSource {
    val api: MoviesApi = retrofit(baseUrl, apiKey).create()

    return MoviesRemoteDataSource(api)
}