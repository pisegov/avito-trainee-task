package com.myaxa.movies.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.myaxa.movies.database.models.EpisodeDBO
import com.myaxa.movies.database.models.MovieDetailsInfoDBO

@Dao
interface EpisodeDao : GenericDetailsDao<EpisodeDBO> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertEpisodeDBOList(list: List<EpisodeDBO>)

    @Query(
        "select * from episodes where movie_id = :movieId " +
            "limit :offset, :rowCount"
    )
    suspend fun getMovieEpisodes(movieId: Long, offset: Int, rowCount: Int): List<EpisodeDBO>

    @Query("select count(*) from episodes where movie_id = :movieId")
    suspend fun getEpisodesCount(movieId: Long): Int

    @Transaction
    override suspend fun insertList(list: List<EpisodeDBO>, movieId: Long) =
        insertEpisodeDBOList(list)

    @Transaction
    override suspend fun getList(movieId: Long, offset: Int, rowCount: Int) =
        getMovieEpisodes(movieId, offset, rowCount) as List<MovieDetailsInfoDBO>

    @Transaction
    override suspend fun getItemsCount(movieId: Long): Int =
        getEpisodesCount(movieId)
}