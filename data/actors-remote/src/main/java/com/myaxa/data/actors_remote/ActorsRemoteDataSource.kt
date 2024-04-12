package com.myaxa.data.actors_remote

import com.myaxa.network.RetrofitModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.create

class ActorsRemoteDataSource internal constructor(private val api: ActorsService) {
    suspend fun getActorsByMovieId(movieId: Long, page: Int, pageSize: Int): Result<ActorsResponseDTO> = withContext(Dispatchers.IO) {
        api.getActorsByMovieId(movieId, page, pageSize)
    }
}

fun ActorsRemoteDataSource(retrofitModule: RetrofitModule): ActorsRemoteDataSource {
    val api = retrofitModule.retrofit.create<ActorsService>()
    return ActorsRemoteDataSource(api)
}