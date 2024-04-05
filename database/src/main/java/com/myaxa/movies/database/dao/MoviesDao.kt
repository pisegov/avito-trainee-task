package com.myaxa.movies.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.myaxa.movies.database.models.MovieDBO
import kotlinx.coroutines.flow.Flow

@Dao
internal interface MoviesDao {

    @Query("select * from movies")
    fun getAll(): Flow<List<MovieDBO>>

    @Upsert
    fun insertList(moves: List<MovieDBO>)

    @Delete
    fun remove(moves: List<MovieDBO>)

    @Query("delete from movies")
    suspend fun clear()
}