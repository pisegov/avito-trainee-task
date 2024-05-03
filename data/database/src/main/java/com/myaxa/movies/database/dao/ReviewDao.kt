package com.myaxa.movies.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.myaxa.movies.database.models.MovieDetailsInfoDBO
import com.myaxa.movies.database.models.ReviewDBO

@Dao
interface ReviewDao : GenericDetailsDao<ReviewDBO> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertReviewDBOList(list: List<ReviewDBO>)

    @Query(
        "select * from review where movie_id = :movieId " +
            "limit :offset, :rowCount"
    )
    suspend fun getMovieReviews(movieId: Long, offset: Int, rowCount: Int): List<ReviewDBO>

    @Query("select count(*) from review where movie_id = :movieId")
    suspend fun getEpisodesCount(movieId: Long): Int

    @Transaction
    override suspend fun insertList(list: List<ReviewDBO>, movieId: Long) =
        insertReviewDBOList(list)

    @Transaction
    override suspend fun getList(movieId: Long, offset: Int, rowCount: Int) =
        getMovieReviews(movieId, offset, rowCount) as List<MovieDetailsInfoDBO>

    @Transaction
    override suspend fun getItemsCount(movieId: Long): Int =
        getEpisodesCount(movieId)
}