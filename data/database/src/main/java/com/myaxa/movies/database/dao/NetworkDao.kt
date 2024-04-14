package com.myaxa.movies.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.myaxa.movies.database.models.MovieDBO
import com.myaxa.movies.database.models.MovieRemoteDBO
import com.myaxa.movies.database.models.NetworkDBO
import com.myaxa.movies.database.models.TypeDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface NetworkDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun _insert(item: NetworkDBO): Long

    @Query("SELECT network_id FROM network WHERE title == :type")
    fun getId(type: String): Long?

    @Transaction
    suspend fun upsert(item: NetworkDBO): Long {
        val id = _insert(item)

        return if (id == -1L) {
            getId(item.title) ?: throw IllegalStateException("ID should not be null")
        } else {
            id
        }
    }

    @Query("select * from network")
    fun getAll(): List<NetworkDBO>
}