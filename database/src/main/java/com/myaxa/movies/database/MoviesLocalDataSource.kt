package com.myaxa.movies.database

import android.content.Context
import androidx.room.Room
import com.myaxa.movies.database.dao.MoviesDao
import com.myaxa.movies.database.models.MovieDBO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

fun MoviesLocalDataSource(applicationContext: Context): MoviesLocalDataSource {
    val roomDb = Room.databaseBuilder(
        applicationContext.applicationContext,
        MoviesDatabase::class.java,
        "movies"
    ).build()
    return MoviesLocalDataSource(roomDb.moviesDao())
}

class MoviesLocalDataSource internal constructor(
    private val dao: MoviesDao,
) {
    fun getAll(): Flow<List<MovieDBO>> {
        return dao.getAll()
    }

    suspend fun insertList(movies: List<MovieDBO>) = withContext(Dispatchers.IO) {
        dao.insertList(movies)
    }

    suspend fun remove(movies: List<MovieDBO>) = withContext(Dispatchers.IO) {
        dao.remove(movies)
    }

    suspend fun clear() = withContext(Dispatchers.IO) {
        dao.clear()
    }
}