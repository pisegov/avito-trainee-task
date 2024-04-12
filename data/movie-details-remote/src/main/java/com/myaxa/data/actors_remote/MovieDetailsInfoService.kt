package com.myaxa.data.actors_remote

import androidx.annotation.IntRange
import com.myaxa.data.actors_remote.models.ActorDTO
import com.myaxa.data.actors_remote.models.ImageDTO
import com.myaxa.data.actors_remote.models.ResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDetailsInfoService {
    @GET("v1.4/person")
    suspend fun getActorsByMovieId(
        @Query("movies.id") @IntRange(from = 250, to = 7000000) movieId: Long,
        @Query(value = "page") @IntRange(from = 1) page: Int = 1,
        @Query(value = "limit") @IntRange(from = 1, to = 20) limit: Int = 10,
        @Query("profession.value") profession: String = "Актер",
    ): Result<ResponseDTO<ActorDTO>>

    @GET("v1.4/image")
    suspend fun getImagesByMovieId(
        @Query("movieId") @IntRange(from = 250, to = 7000000) movieId: Long,
        @Query(value = "page") @IntRange(from = 1) page: Int = 1,
        @Query(value = "limit") @IntRange(from = 1, to = 20) limit: Int = 10,
    ): Result<ResponseDTO<ImageDTO>>
}