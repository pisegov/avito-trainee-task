package com.myaxa.movies.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.myaxa.movies.database.models.ActorDBO
import com.myaxa.movies.database.models.MovieActorCrossRef
import com.myaxa.movies.database.models.MovieDetailsInfoDBO

@Dao
interface ActorDao : GenericDetailsDao<ActorDBO> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovieActorCrossRefs(list: List<MovieActorCrossRef>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertActorDBOList(list: List<ActorDBO>)

    @Transaction
    fun insertActorsList(list: List<ActorDBO>, movieId: Long) {
        insertActorDBOList(list)
        insertMovieActorCrossRefs(list.map { MovieActorCrossRef(movieId, it.id) })
    }

    @Query(
        "select actors.* from actors " +
            "join movies_actors on actors.actor_id = movies_actors.actor_id " +
            "where movies_actors.movie_id = :movieId " +
            "limit :offset, :rowCount"
    )
    suspend fun getMovieActors(movieId: Long, offset: Int, rowCount: Int): List<ActorDBO>

    @Query(
        "select count(*) from actors " +
            "join movies_actors on actors.actor_id = movies_actors.actor_id " +
            "where movies_actors.movie_id = :movieId "
    )
    suspend fun getActorsCount(movieId: Long): Int

    @Transaction
    override suspend fun insertList(list: List<ActorDBO>, movieId: Long) =
        insertActorsList(list, movieId)

    @Transaction
    override suspend fun getList(movieId: Long, offset: Int, rowCount: Int) =
        getMovieActors(movieId, offset, rowCount) as List<MovieDetailsInfoDBO>

    @Transaction
    override suspend fun getItemsCount(movieId: Long): Int =
        getActorsCount(movieId)
}