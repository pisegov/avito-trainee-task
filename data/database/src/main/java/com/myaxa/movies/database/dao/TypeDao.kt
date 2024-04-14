package com.myaxa.movies.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.myaxa.movies.database.models.TypeDBO

@Dao
interface TypeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun _insert(item: TypeDBO): Long

    @Query("SELECT type_id FROM content_type WHERE title == :type")
    fun getId(type: String): Long?

    @Transaction
    suspend fun upsert(item: TypeDBO): Long {
        val id = _insert(item)

        return if (id == -1L) {
            getId(item.title) ?: throw IllegalStateException("type ID should not be null")
        } else {
            id
        }
    }

    @Query("select * from content_type")
    fun getAll(): List<TypeDBO>
}