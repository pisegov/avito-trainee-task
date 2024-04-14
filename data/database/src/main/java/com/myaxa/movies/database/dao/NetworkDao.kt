package com.myaxa.movies.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.myaxa.movies.database.models.NetworkDBO

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