package com.myaxa.movies.database.datasources

import androidx.room.withTransaction
import com.myaxa.movies.database.MoviesDatabase
import com.myaxa.movies.database.MoviesDatabaseModule
import com.myaxa.movies.database.models.MovieFullDBO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MoviesLocalDataSource internal constructor(
    private val database: MoviesDatabase,
) {
    private val dao
        get() = database.dao

    fun getMovie(id: Long) : Flow<MovieFullDBO?> {
        return database.dao.getMovieById(id)
    }

    suspend fun insertMovie(movieFull: MovieFullDBO) = withContext(Dispatchers.IO) {
        val movie = movieFull.movie

        database.withTransaction {
            val typeId = database.typeDao.upsert(movieFull.type)
            val ageRatingId = movieFull.ageRating?.let { database.ageRatingDao.upsert(it) }
            val networkId = movieFull.network?.let { database.networkDao.upsert(it) }

            val movieDBO = movie.copy(typeId = typeId, ageRatingId = ageRatingId, networkId = networkId)
            database.dao.insertMovie(movieDBO)

            database.genreDao.insertGenresWithMovieId(movieFull.genres, movie.id)
        }
    }

    suspend fun insertList(movies: List<MovieFullDBO>) = withContext(Dispatchers.IO) {
        movies.forEach {
            insertMovie(it)
        }
    }
}

fun MoviesLocalDataSource(databaseModule: MoviesDatabaseModule): MoviesLocalDataSource {
    return MoviesLocalDataSource(databaseModule.db)
}