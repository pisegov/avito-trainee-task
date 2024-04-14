package com.myaxa.movies.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "content_type",
    indices = [Index(value = ["title"], unique = true)]
)
data class TypeDBO(
    @ColumnInfo("type_id") @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("title") val title: String,
)