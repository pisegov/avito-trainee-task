package com.myaxa.movies.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.myaxa.movies.database.models.AgeRatingDBO
import com.myaxa.movies.database.models.GenreDBO
import com.myaxa.movies.database.models.MovieGenreCrossRef

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun _insert(item: GenreDBO): Long

    @Query("SELECT genre_id FROM genres WHERE title like :title")
    fun getId(title: String): Long?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieGenreCrossRef(movieGenreCrossRef: MovieGenreCrossRef)

    @Transaction
    suspend fun upsert(item: GenreDBO): Long {
        val id = _insert(item)

        return if (id == -1L) {
            getId(item.title) ?: throw IllegalStateException("ID should not be null")
        } else {
            id
        }
    }


    @Transaction
    suspend fun insertGenresWithMovieId(genres: List<GenreDBO>, movieId: Long) {
        genres.forEach {genre ->
            val id = upsert(genre)
            insertMovieGenreCrossRef(MovieGenreCrossRef(movieId, id))
        }
    }

    @Query("select * from genres")
    fun getAll(): List<GenreDBO>
}