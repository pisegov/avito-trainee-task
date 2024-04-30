package com.myaxa.movies.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.myaxa.movies.database.models.CountryDBO
import com.myaxa.movies.database.models.MovieCountryCrossRef

@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEntity(item: CountryDBO): Long

    @Query("SELECT country_id FROM countries WHERE title like :title")
    fun getId(title: String): Long?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCrossRef(crossRef: MovieCountryCrossRef)

    @Transaction
    suspend fun insertEntityAndGetId(item: CountryDBO): Long {
        val id = insertEntity(item)

        return if (id == -1L) {
            getId(item.title) ?: throw IllegalStateException("ID should not be null")
        } else {
            id
        }
    }

    @Transaction
    suspend fun insertCountriesWithMovieId(items: List<CountryDBO>, movieId: Long) {
        items.forEach { item ->
            val id = insertEntityAndGetId(item)
            insertCrossRef(MovieCountryCrossRef(movieId, id))
        }
    }

    @Query("select * from countries")
    fun getAll(): List<CountryDBO>
}
