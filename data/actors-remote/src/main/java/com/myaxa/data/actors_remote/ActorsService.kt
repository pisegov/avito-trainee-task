package com.myaxa.data.actors_remote

import androidx.annotation.IntRange
import retrofit2.http.GET
import retrofit2.http.Query

interface ActorsService {

    @GET("v1.4/person")
    suspend fun getActorsByMovieId(
        @Query("movies.id") @IntRange(from = 250, to = 7000000) movieId: Long,
        @Query(value = "page") @IntRange(from = 1) page: Int = 1,
        @Query(value = "limit") @IntRange(from = 1, to = 20) limit: Int = 10,
        @Query("profession.value") profession: String = "Актер",
    ): Result<ActorsResponseDTO>
}
