package com.myaxa.movies.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "review",
    foreignKeys = [
        ForeignKey(
            entity = MovieDBO::class,
            parentColumns = ["movie_id"],
            childColumns = ["movie_id"],
        )
    ]
)
data class ReviewDBO(
    @ColumnInfo("id") @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("movie_id") val movieId: Long,
    @ColumnInfo("author") val author: String,
    @ColumnInfo("date") val date: String,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("text") val text: String,
)