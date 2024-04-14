package com.myaxa.movies.database.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    tableName = "movies",
    foreignKeys = [
        ForeignKey(
            entity = TypeDBO::class,
            parentColumns = ["type_id"],
            childColumns = ["type_id"],
        ),
        ForeignKey(
            entity = AgeRatingDBO::class,
            parentColumns = ["age_rating_id"],
            childColumns = ["age_rating_id"],
        ),
        ForeignKey(
            entity = NetworkDBO::class,
            parentColumns = ["network_id"],
            childColumns = ["network_id"],
        ),
    ]
)
data class MovieDBO(
    @ColumnInfo("movie_id") @PrimaryKey val id: Long,
    @ColumnInfo("name") val name: String? = null,
    @ColumnInfo("year") val year: Int? = null,
    @ColumnInfo("poster") val poster: String? = null,
    @ColumnInfo("backdrop") val backdrop: String? = null,
    @ColumnInfo("description") val description: String? = null,
    @ColumnInfo("is_series") val isSeries: Boolean,
    @ColumnInfo("rating") val rating: Double? = null,

    @ColumnInfo("type_id") val typeId: Long,
    @ColumnInfo("age_rating_id") val ageRatingId: Long?,
    @ColumnInfo("network_id") val networkId: Long?,
)

data class MovieRemoteDBO(
    @Embedded val movie: MovieDBO,

    val type: TypeDBO,

    val ageRating: AgeRatingDBO?,

    val network: NetworkDBO?,

    @Relation(
        parentColumn = "movie_id",
        entity = GenreDBO::class,
        entityColumn = "genre_id",
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val genres: List<GenreDBO>,

    @Relation(
        parentColumn = "movie_id",
        entity = CountryDBO::class,
        entityColumn = "country_id",
        associateBy = Junction(MovieCountryCrossRef::class)
    )
    val countries: List<CountryDBO>,
)