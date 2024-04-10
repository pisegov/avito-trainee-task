package com.myaxa.data.movie_details_remote

import com.myaxa.data.movie_details_remote.models.MovieDetailsDTO
import com.myaxa.network.RetrofitModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDetailsRemoteDataSource internal constructor(
    private val api: MovieDetailsService,
) {
    suspend fun getMovieDetails(id: Long): Result<MovieDetailsDTO> = withContext(Dispatchers.IO) {
        api.movie(id)
    }
}


fun MovieDetailsRemoteDataSource(retrofitModule: RetrofitModule): MovieDetailsRemoteDataSource {
    val api = retrofitModule.retrofit.create(MovieDetailsService::class.java)

    return MovieDetailsRemoteDataSource(api)
}