package com.myaxa.movies.database.datasources

import com.myaxa.movies.database.MoviesDatabase
import com.myaxa.movies.database.MoviesDatabaseModule
import com.myaxa.movies.database.models.MovieDBO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext

class MoviesLocalDataSource internal constructor(
    private val database: MoviesDatabase,
) {
    private val dao
        get() = database.dao
    fun getAll(): Flow<List<MovieDBO>> {
        return dao.getAll().distinctUntilChanged()
    }

    fun getMoviesWithId(ids: List<Long>): Flow<List<MovieDBO>> {
        return dao.getMoviesWithId(ids).distinctUntilChanged()
    }

    fun getMovie(id: Long) : Flow<MovieDBO?> {
        return database.dao.getMovieById(id)
    }

    suspend fun insertMovie(movieDBO: MovieDBO) = withContext(Dispatchers.IO){
        database.dao.insertMovie(movieDBO)
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

fun MoviesLocalDataSource(databaseModule: MoviesDatabaseModule): MoviesLocalDataSource {
    return MoviesLocalDataSource(databaseModule.db)
}