package com.myaxa.movies.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.myaxa.domain.movie_details.Episode

@Entity(
    tableName = "episodes",
    foreignKeys = [
        ForeignKey(
            entity = MovieDBO::class,
            parentColumns = ["movie_id"],
            childColumns = ["movie_id"],
        )
    ],
)
data class EpisodeDBO(
    @ColumnInfo("id") @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("movie_id") val movieId: Long,
    @ColumnInfo("season_number") val seasonNumber: Int,
    @ColumnInfo("episode_number") val episodeNumber: Int,
    @ColumnInfo("date") val date: String,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("photo") val photo: String? = null,
) : MovieDetailsInfoDBO {
    companion object : MovieDetailsInfoDBOCreator<Episode, EpisodeDBO> {
        override fun fromDomainModel(domainModel: Episode): EpisodeDBO = with(domainModel) {
            EpisodeDBO(
                movieId = movieId,
                seasonNumber = seasonNumber,
                episodeNumber = episodeNumber,
                date = date,
                title = title,
                photo = image,
            )
        }
    }

    override fun toDomainModel() = Episode(
        movieId = movieId,
        seasonNumber = seasonNumber,
        episodeNumber = episodeNumber,
        date = date,
        title = title,
        image = photo,
    )
}