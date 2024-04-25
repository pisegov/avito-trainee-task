package com.myaxa.data.actors_remote.models

import com.myaxa.data.actors_remote.DateSerializer
import com.myaxa.domain.movie_details.DetailsInfoModel
import com.myaxa.domain.movie_details.Episode
import com.myaxa.domain.movie_details.Season
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Serializable
data class SeasonDTO(
    @SerialName("movieId") val movieId: Long,
    @SerialName("number") val number: Int,
    @SerialName("episodes") val episodes: List<EpisodeDTO>,
) : InfoDTO {
    override fun toDomainModel(): DetailsInfoModel =
        Season(
            movieId = movieId,
            number = number,
            episodes = episodes.map { it.toDomainModel(number) }
        )
}

@Serializable
data class EpisodeDTO(
    @SerialName("number") val number: Int,
    @SerialName("name") val title: String,
    @SerialName("still") val photo: Poster? = null,
    @SerialName("airDate") @Serializable(with = DateSerializer::class) val date: Instant? = null,
) {
    fun toDomainModel(seasonNumber: Int): Episode {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
            .withZone(ZoneId.systemDefault())

        return Episode(
            seasonNumber = seasonNumber,
            episodeNumber = number,
            title = title,
            date = date?.let { formatter.format(date) } ?: "",
            image = photo?.url,
        )
    }
}