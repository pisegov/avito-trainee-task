package com.myaxa.movies.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.myaxa.domain.movie_details.Image

@Entity(
    tableName = "images",
    foreignKeys = [
        ForeignKey(
            entity = MovieDBO::class,
            parentColumns = ["movie_id"],
            childColumns = ["movie_id"],
        )
    ]
)
data class ImageDBO(
    @ColumnInfo("id") @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("movie_id") val movieId: Long,
    @ColumnInfo("photo") val photo: String,
): MovieDetailsInfoDBO {
    companion object: MovieDetailsInfoDBOCreator<Image, ImageDBO> {
        override fun fromDomainModel(domainModel: Image): ImageDBO = with(domainModel){
            ImageDBO(movieId = movieId, photo = url)
        }
    }
    override fun toDomainModel() = Image(movieId = movieId, url = photo)
}