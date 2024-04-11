package com.myaxa.movies.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.myaxa.movies.database.models.MovieDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("select * from movies")
    fun getAll(): Flow<List<MovieDBO>>

    @Query("select * from movies where id like :id")
    fun getMovieById(id: Long): Flow<MovieDBO?>

    @Upsert
    fun insertMovie(movieDBO: MovieDBO)

    @Query("select * from movies where id in (:ids)")
    fun getMoviesWithId(ids: List<Long>): Flow<List<MovieDBO>>

    @Query("SELECT * FROM movies WHERE :ids IS NULL OR :ids = '' OR id IN (:ids)")
    fun getEntitiesByIds(ids: List<Int>?): Flow<List<MovieDBO>>

    @Upsert
    fun insertList(moves: List<MovieDBO>)

    @Delete
    fun remove(moves: List<MovieDBO>)

    @Query("delete from movies")
    suspend fun clear()
}