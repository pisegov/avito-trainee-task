package com.myaxa.data.movie_details_remote

import androidx.annotation.IntRange
import com.myaxa.data.movie_details_remote.models.MovieDetailsDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface MovieDetailsService {
    @GET("v1.4/movie/{id}")
    suspend fun movie(
        @Path(value = "id") @IntRange(from = 250, to = 7000000) id: Long,
    ): Result<MovieDetailsDTO>
}

