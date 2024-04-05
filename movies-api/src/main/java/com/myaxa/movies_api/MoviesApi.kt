package com.myaxa.movies_api

import androidx.annotation.IntRange
import com.myaxa.movies_api.models.ResponseDTO
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MoviesApi {
    @GET("movie")
    suspend fun all(
        @Query(value = "page") @IntRange(from = 1) page: Int = 1,
        @Query(value = "limit") @IntRange(from = 1, to = 20) limit: Int = 10,
    ): Result<ResponseDTO>

    @GET("movie/search")
    suspend fun search(
        @Query(value = "page") @IntRange(from = 1) page: Int = 1,
        @Query(value = "query") query: String = "",
    )
}

internal fun retrofit(
    baseUrl: String,
    apiKey: String,
): Retrofit {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor(apiKey))
        .build()

    val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    val jsonConverterFactory = json.asConverterFactory(MediaType.get("application/json"))

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(jsonConverterFactory)
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .client(okHttpClient)
        .build()
}

