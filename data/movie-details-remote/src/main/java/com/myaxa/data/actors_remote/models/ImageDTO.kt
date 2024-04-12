package com.myaxa.data.actors_remote.models

import com.myaxa.domain.movie_details.DetailsInfoModel
import com.myaxa.domain.movie_details.Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageDTO(
    @SerialName("movieId") val movieId: Long,
    @SerialName("url") val url: String,
): InfoDTO {
    override fun toDomainModel(): DetailsInfoModel = Image(
        movieId = movieId,
        url = url,
    )
}