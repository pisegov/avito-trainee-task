package com.myaxa.movies_api

import androidx.annotation.IntRange
import com.myaxa.movies_api.models.FilterOptionDTO
import com.myaxa.movies_api.models.ResponseDTO
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MoviesApi {
    @GET("v1.4/movie/search")
    suspend fun search(
        @Query(value = "query") query: String = "",
        @Query(value = "page") @IntRange(from = 1) page: Int = 1,
        @Query(value = "limit") @IntRange(from = 1, to = 20) limit: Int = 10,
    ): Result<ResponseDTO>


    @GET("v1.4/movie")
    suspend fun emptyQueryMovies(
        @Query(value = "page") @IntRange(from = 1) page: Int = 1,
        @Query(value = "limit") @IntRange(from = 1, to = 20) limit: Int = 10,
        @Query(value = "year") year: List<String>?,
        @Query(value = "rating.kp") rating: List<String>?,
    ): Result<ResponseDTO>

    @GET("v1.4/movie")
    suspend fun filter(
        @Query(value = "id") ids: List<Long> = emptyList(),
        @Query(value = "year") year: List<String>?,
        @Query(value = "rating.kp") rating: List<String>?,
    ): Result<ResponseDTO>

    @GET("v1/movie/possible-values-by-field")
    suspend fun getFilterOptions(
        @Query(value = "field") filter: String,
    ): Result<List<FilterOptionDTO>>
}

internal fun retrofit(
    baseUrl: String,
    apiKey: String,
): Retrofit {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor(apiKey))
        .addInterceptor(loggingInterceptor)
        .build()

    val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    val jsonConverterFactory = json.asConverterFactory("application/json".toMediaType())

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(jsonConverterFactory)
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .client(okHttpClient)
        .build()
}

