package com.myaxa.data.actors_remote

import androidx.annotation.IntRange
import com.myaxa.data.actors_remote.models.ActorDTO
import com.myaxa.data.actors_remote.models.ImageDTO
import com.myaxa.data.actors_remote.models.ResponseDTO
import com.myaxa.data.actors_remote.models.ReviewDTO
import com.myaxa.data.actors_remote.models.SeasonDTO
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MovieDetailsInfoService {
    @GET("v1.4/person")
    suspend fun getActorsByMovieId(
        @Query("movies.id") @IntRange(from = 250, to = 7000000) movieId: Long,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1, to = 20) limit: Int = 10,
        @Query("profession.value") profession: List<String> = listOf("Актер"),
        @Query("sortType") sortType: List<String> = listOf("-1"),
        @Query("sortField") sortField: List<String> = listOf("profession.value"),
    ): Result<ResponseDTO<ActorDTO>>

    @GET("v1.4/image")
    suspend fun getImagesByMovieId(
        @Query("movieId") @IntRange(from = 250, to = 7000000) movieId: Long,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1, to = 20) limit: Int = 10,
        @Query("type") type: List<String> = listOf("!cover"),
    ): Result<ResponseDTO<ImageDTO>>

    @GET("v1.4/review")
    suspend fun getReviewsByMovieId(
        @Query("movieId") @IntRange(from = 250, to = 7000000) movieId: Long,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1, to = 20) limit: Int = 10,
    ): Result<ResponseDTO<ReviewDTO>>

    @GET("v1.4/season")
    suspend fun getSeasonsByMovieId(
        @Query("movieId") @IntRange(from = 250, to = 7000000) movieId: Long,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1, to = 20) limit: Int = 10,
        @Query("sortType") sortType: List<String> = listOf("1"),
        @Query("sortField") sortField: List<String> = listOf("number"),
    ): Result<ResponseDTO<SeasonDTO>>
}