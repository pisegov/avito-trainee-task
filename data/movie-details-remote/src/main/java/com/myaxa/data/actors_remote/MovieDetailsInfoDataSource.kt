package com.myaxa.data.actors_remote

import com.myaxa.domain.movie_details.Actor
import com.myaxa.domain.movie_details.DetailsInfoModel
import com.myaxa.domain.movie_details.Episode
import com.myaxa.domain.movie_details.Review
import com.myaxa.network.RetrofitModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.create

class MovieDetailsInfoDataSource internal constructor(private val api: MovieDetailsInfoService) {
    suspend fun <T : DetailsInfoModel> getInfoByMovieId(
        type: Class<T>,
        movieId: Long,
        page: Int,
        pageSize: Int,
    ) = withContext(Dispatchers.IO) {
        when (type) {
            Actor::class.java -> {
               api.getActorsByMovieId(movieId, page, pageSize)
            }
            Review::class.java -> {
                api.getReviewsByMovieId(movieId, page, pageSize)
            }

            Episode::class.java -> {
                api.getSeasonsByMovieId(movieId, page, pageSize)
            }
            else -> {
                api.getImagesByMovieId(movieId, page, pageSize)
            }
        }
    }
}

fun MovieDetailsInfoDataSource(retrofitModule: RetrofitModule): MovieDetailsInfoDataSource {
    val api = retrofitModule.retrofit.create<MovieDetailsInfoService>()
    return MovieDetailsInfoDataSource(api)
}