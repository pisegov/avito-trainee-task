package com.myaxa.movies.database.datasources

import com.myaxa.domain.movie_details.Actor
import com.myaxa.domain.movie_details.DetailsInfoModel
import com.myaxa.domain.movie_details.Episode
import com.myaxa.domain.movie_details.Image
import com.myaxa.domain.movie_details.Review
import com.myaxa.movies.database.MoviesDatabase
import com.myaxa.movies.database.MoviesDatabaseModule
import com.myaxa.movies.database.dao.GenericDetailsDao
import com.myaxa.movies.database.models.ActorDBO
import com.myaxa.movies.database.models.EpisodeDBO
import com.myaxa.movies.database.models.ImageDBO
import com.myaxa.movies.database.models.MovieDetailsInfoDBO
import com.myaxa.movies.database.models.ReviewDBO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDetailsInfoLocalDataSource internal constructor(private val db: MoviesDatabase) {

    suspend fun <T : DetailsInfoModel> insertList(
        list: List<DetailsInfoModel>,
        movieId: Long,
        type: Class<T>,
    ) = withContext(Dispatchers.IO) {
        when (type) {
            Actor::class.java -> {
                db.actorDao.insertList((list as List<Actor>).map { ActorDBO.fromDomainModel(it) }, movieId)
            }

            Review::class.java -> {
                db.reviewDao.insertList((list as List<Review>).map { ReviewDBO.fromDomainModel(it) }, movieId)
            }

            Image::class.java -> {
                db.imageDao.insertList((list as List<Image>).map { ImageDBO.fromDomainModel(it) }, movieId)
            }

            Episode::class.java -> {
                db.episodeDao.insertList((list as List<Episode>).map { EpisodeDBO.fromDomainModel(it) }, movieId)
            }
        }
    }

    suspend fun <T : DetailsInfoModel> getList(
        movieId: Long,
        type: Class<T>,
        page: Int,
        pageSize: Int,
    ): List<MovieDetailsInfoDBO> {
        val offset = page * pageSize - pageSize
        val dao = getDaoByType(type)

        return dao.getList(movieId, offset, pageSize)
    }

    suspend fun <T> getPagesCount(movieId: Long, pageSize: Int, type: Class<T>): Int = withContext(Dispatchers.IO) {

        val dao = getDaoByType(type)
        val moviesCount = dao.getItemsCount(movieId)
        val reminder = moviesCount % pageSize

        moviesCount / pageSize + if (reminder > 0) 1 else 0
    }

    private fun <T> getDaoByType(type: T): GenericDetailsDao<out MovieDetailsInfoDBO> = when (type) {
        Actor::class.java -> {
            db.actorDao
        }

        Review::class.java -> {
            db.reviewDao
        }

        Image::class.java -> {
            db.imageDao
        }

        /*Episode::class.java*/ else -> {
            db.episodeDao
        }
    }
}

fun MovieDetailsInfoLocalDataSource(databaseModule: MoviesDatabaseModule): MovieDetailsInfoLocalDataSource {
    return MovieDetailsInfoLocalDataSource(databaseModule.db)
}