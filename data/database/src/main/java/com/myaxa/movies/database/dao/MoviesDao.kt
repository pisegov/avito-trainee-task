package com.myaxa.movies.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.myaxa.movies.database.models.MovieDBO
import com.myaxa.movies.database.models.MovieFullDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("select * from movies where movie_id in (:ids) limit :offset, :rowCount")
    fun getMovieListByIds(ids: List<Long>, offset: Int, rowCount: Int): List<MovieFullDBO>

    @Query("select * from movies where movie_id like :id")
    fun getMovieById(id: Long): Flow<MovieFullDBO?>

    @Query("select count(*) from movies")
    fun getMoviesCount(): Int

    @Upsert
    suspend fun insertMovie(movieDBO: MovieDBO)
}