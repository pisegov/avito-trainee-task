package com.myaxa.movies.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "genres",
    indices = [Index(value = ["title"], unique = true)]
)
data class GenreDBO(
    @ColumnInfo("genre_id") @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("title") val title: String,
)

@Entity(
    tableName = "movie_genre",
    primaryKeys = ["movie_id", "genre_id"],
    foreignKeys = [
        ForeignKey(
            entity = MovieDBO::class,
            parentColumns = ["movie_id"],
            childColumns = ["movie_id"],
        ),
        ForeignKey(
            entity = GenreDBO::class,
            parentColumns = ["genre_id"],
            childColumns = ["genre_id"],
        ),
    ]
)
data class MovieGenreCrossRef(
    @ColumnInfo("movie_id") val movieId: Long,
    @ColumnInfo("genre_id") val genreId: Long,
)