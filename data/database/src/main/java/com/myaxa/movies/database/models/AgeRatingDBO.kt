package com.myaxa.movies.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "age_rating",
    indices = [Index(value = ["title"], unique = true)]
)
data class AgeRatingDBO(
    @ColumnInfo("age_rating_id") @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo("title") val title: String,
)