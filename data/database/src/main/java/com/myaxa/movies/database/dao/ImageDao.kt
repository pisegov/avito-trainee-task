package com.myaxa.movies.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.myaxa.movies.database.models.ImageDBO
import com.myaxa.movies.database.models.MovieDetailsInfoDBO

@Dao
interface ImageDao : GenericDetailsDao<ImageDBO> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertImageDBOList(list: List<ImageDBO>)

    @Query(
        "select * from images where movie_id = :movieId " +
            "limit :offset, :rowCount"
    )
    suspend fun getMovieImages(movieId: Long, offset: Int, rowCount: Int): List<ImageDBO>

    @Query("select count(*) from images where movie_id = :movieId")
    suspend fun getImagesCount(movieId: Long): Int

    @Transaction
    override suspend fun insertList(list: List<ImageDBO>, movieId: Long) {
        insertImageDBOList(list)
    }

    @Transaction
    override suspend fun getList(movieId: Long, offset: Int, rowCount: Int) =
        getMovieImages(movieId, offset, rowCount) as List<MovieDetailsInfoDBO>

    @Transaction
    override suspend fun getItemsCount(movieId: Long): Int =
        getImagesCount(movieId)
}