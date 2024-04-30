package com.myaxa.movies.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "countries",
    indices = [Index(value = ["title"], unique = true)],
)
data class CountryDBO(
    @ColumnInfo("country_id") @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("title") val title: String,
)

@Entity(
    tableName = "movie_country",
    primaryKeys = ["movie_id", "country_id"],
    foreignKeys = [
        ForeignKey(
            entity = MovieDBO::class,
            parentColumns = ["movie_id"],
            childColumns = ["movie_id"],
        ),
        ForeignKey(
            entity = CountryDBO::class,
            parentColumns = ["country_id"],
            childColumns = ["country_id"],
        ),
    ]
)
data class MovieCountryCrossRef(
    @ColumnInfo("movie_id") val movieId: Long,
    @ColumnInfo("country_id") val countryId: Long,
)