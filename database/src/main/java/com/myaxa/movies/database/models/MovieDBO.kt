package com.myaxa.movies.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieDBO(
    @ColumnInfo("id") @PrimaryKey val id: Long,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("type") val type: String,
    @ColumnInfo("typeNumber") val typeNumber: Int,
    @ColumnInfo("year") val year: Int,
    @ColumnInfo("description") val description: String? = null,
    @ColumnInfo("shortDescription") val shortDescription: String? = null,
    @ColumnInfo("slogan") val slogan: String? = null,
    @ColumnInfo("rating") val rating: Double? = null,
    @ColumnInfo("votes") val votes: Long? = null,
    @ColumnInfo("ageRating") val ageRating: Int? = null,
    @ColumnInfo("logoUrl") val logoUrl: String? = null,
    @ColumnInfo("poster") val poster: String? = null,
    @ColumnInfo("backdrop") val backdrop: String? = null,
    // @ColumnInfo("genres") val genres: List<String> = listOf(),
    // @ColumnInfo("persons") val persons: List<Persons> = listOf(),
    @ColumnInfo("reviewCount") val reviewCount: Int? = null,
    // @ColumnInfo("seasonsInfo") val seasonsInfo: List<SeasonsInfo> = listOf(),
    // @ColumnInfo("totalSeriesLength") val totalSeriesLength: Int? = null,
    // @ColumnInfo("seriesLength") val seriesLength: Int? = null,
    @ColumnInfo("isSeries") val isSeries: Boolean,
    // @ColumnInfo("networks") val networks: Networks? = Networks(),
)