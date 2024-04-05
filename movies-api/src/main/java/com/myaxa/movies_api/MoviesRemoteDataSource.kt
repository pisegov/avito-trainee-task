package com.myaxa.movies_api

import com.myaxa.movies_api.models.ResponseDTO
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.create

class MoviesRemoteDataSource @Inject internal constructor(
    private val moviesApi: MoviesApi,
) {
    suspend fun getAll(): Result<ResponseDTO> = withContext(Dispatchers.IO) {
        moviesApi.all()
    }
}

fun MoviesRemoteDataSource(
    baseUrl: String,
    apiKey: String,
): MoviesRemoteDataSource {
    val api: MoviesApi = retrofit(baseUrl, apiKey).create()

    return MoviesRemoteDataSource(api)
}