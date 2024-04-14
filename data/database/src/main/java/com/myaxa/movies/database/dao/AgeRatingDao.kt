package com.myaxa.movies.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.myaxa.movies.database.models.AgeRatingDBO

@Dao
interface AgeRatingDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun _insert(item: AgeRatingDBO): Long

    @Query("SELECT age_rating_id FROM age_rating WHERE title like :type")
    fun getId(type: String): Long?

    @Transaction
    suspend fun upsert(item: AgeRatingDBO): Long {
        val id = _insert(item)

        return if (id == -1L) {
            getId(item.title) ?: throw IllegalStateException("ID should not be null")
        } else {
            id
        }
    }

    @Query("select * from age_rating")
    fun getAll(): List<AgeRatingDBO>
}