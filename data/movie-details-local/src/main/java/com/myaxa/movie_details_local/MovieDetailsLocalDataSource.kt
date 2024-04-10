package com.myaxa.movie_details_local

import com.myaxa.movies.database.MoviesDatabase
import com.myaxa.movies.database.MoviesDatabaseModule
import com.myaxa.movies.database.models.MovieDBO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MovieDetailsLocalDataSource internal constructor(
    private val database: MoviesDatabase,
){
    fun getMovie(id: Long) : Flow<MovieDBO?> {
        return database.dao.getMovieById(id)
    }

    suspend fun insertMovie(movieDBO: MovieDBO) = withContext(Dispatchers.IO){
        database.dao.insertMovie(movieDBO)
    }
}

fun MovieDetailsLocalDataSource(databaseModule: MoviesDatabaseModule): MovieDetailsLocalDataSource {
    return MovieDetailsLocalDataSource(databaseModule.db)
}
