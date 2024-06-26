package com.myaxa.movies.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.myaxa.movies.database.models.MovieDBO
import com.myaxa.movies.database.models.MovieFullDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("select * from movies")
    fun getAll(): List<MovieDBO>

    @Query("select * from movies where movie_id like :id")
    fun getMovieById(id: Long): Flow<MovieFullDBO?>

    @Upsert
    suspend fun insertMovie(movieDBO: MovieDBO)
}