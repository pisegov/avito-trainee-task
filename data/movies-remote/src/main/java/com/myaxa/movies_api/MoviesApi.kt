package com.myaxa.movies_api

import androidx.annotation.IntRange
import com.myaxa.movies_api.models.FilterOptionDTO
import com.myaxa.movies_api.models.MovieDTO
import com.myaxa.movies_api.models.ResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path
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
        @Query(value = "countries.name") countries: List<String>?,
        @Query(value = "type") types: List<String>?,
        @Query(value = "networks.items.name") networks: List<String>?,
        @Query(value = "genres.name") genres: List<String>?,
        @Query(value = "ageRating") ageRatings: List<String>?,
        @Query(value = "selectFields") selectFields: List<String> = listOf(
            "id",
            "name",
            "type",
            "year",
            "rating",
            "ageRating",
            "poster",
            "backdrop",
            "description",
            "isSeries",
            "genres",
            "countries",
            "networks",
        ),
    ): Result<ResponseDTO>

    @GET("v1.4/movie")
    suspend fun filter(
        @Query(value = "id") ids: List<Long> = emptyList(),
        @Query(value = "year") year: List<String>?,
        @Query(value = "rating.kp") rating: List<String>?,
        @Query(value = "countries.name") countries: List<String>?,
        @Query(value = "type") types: List<String>?,
        @Query(value = "networks.items.name") networks: List<String>?,
        @Query(value = "genres.name") genres: List<String>?,
        @Query(value = "ageRating") ageRatings: List<String>?,
        @Query(value = "selectFields") selectFields: List<String> = listOf(
            "id",
            "name",
            "type",
            "year",
            "rating",
            "ageRating",
            "poster",
            "backdrop",
            "description",
            "isSeries",
            "genres",
            "countries",
            "networks",
        )
    ): Result<ResponseDTO>

    @GET("v1/movie/possible-values-by-field")
    suspend fun getFilterOptions(
        @Query(value = "field") filter: String,
    ): Result<List<FilterOptionDTO>>

    @GET("v1.4/movie/{id}")
    suspend fun movie(
        @Path(value = "id") @IntRange(from = 250, to = 7000000) id: Long,
    ): Result<MovieDTO>
}
