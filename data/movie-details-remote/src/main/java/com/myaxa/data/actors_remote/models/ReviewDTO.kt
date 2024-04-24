package com.myaxa.data.actors_remote.models

import com.myaxa.data.actors_remote.DateSerializer
import com.myaxa.domain.movie_details.Review
import com.myaxa.domain.movie_details.ReviewType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Serializable
data class ReviewDTO(
    @SerialName("id") val id: Long,
    @SerialName("movieId") val movieId: Long,
    @SerialName("author") val author: String,
    @SerialName("title") val title: String?,
    @SerialName("type") val type: ReviewTypeDTO,
    @SerialName("review") val review: String,
    @SerialName("date") @Serializable(with = DateSerializer::class) val date: Instant,
) : InfoDTO {
    override fun toDomainModel(): Review {
        val formatter = DateTimeFormatter.ofPattern ("dd MMMM yyyy")
            .withZone(ZoneId.systemDefault())

        return Review(
            id = id,
            movieId = movieId,
            author = author,
            title = title ?: "",
            type = type.toReviewType(),
            review = review,
            date = formatter.format(date),
        )
    }
}

@Serializable
enum class ReviewTypeDTO {
    @SerialName("Позитивный")
    POSITIVE,

    @SerialName("Нейтральный")
    NEUTRAL,

    @SerialName("Негативный")
    NEGATIVE,
}

fun ReviewTypeDTO.toReviewType() =
    when (this) {
        ReviewTypeDTO.POSITIVE -> ReviewType.POSITIVE
        ReviewTypeDTO.NEUTRAL -> ReviewType.NEUTRAL
        ReviewTypeDTO.NEGATIVE -> ReviewType.NEGATIVE
    }
