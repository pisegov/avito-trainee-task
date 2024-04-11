package com.myaxa.movies.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieDBO(
    @ColumnInfo("id") @PrimaryKey val id: Long,
    @ColumnInfo("name") val name: String?,
    @ColumnInfo("type") val type: String,
    @ColumnInfo("year") val year: Int?,
    @ColumnInfo("rating") val rating: Double? = null,
    @ColumnInfo("reviewCount") val reviewCount: Int? = null,
    @ColumnInfo("ageRating") val ageRating: Int? = null,
    @ColumnInfo("poster") val poster: String? = null,
    @ColumnInfo("backdrop") val backdrop: String? = null,
    @ColumnInfo("description") val description: String? = null,
    @ColumnInfo("shortDescription") val shortDescription: String? = null,
    @ColumnInfo("isSeries") val isSeries: Boolean,
    // @ColumnInfo("networks") val networks: Networks? = Networks(),
    // @ColumnInfo("genres") val genres: List<String> = listOf(),
    // @ColumnInfo("persons") val persons: List<Persons> = listOf(),
    // @ColumnInfo("seasons") val seasons: List<Season> = listOf(),
)