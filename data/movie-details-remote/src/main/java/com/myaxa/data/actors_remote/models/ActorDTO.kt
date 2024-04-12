package com.myaxa.data.actors_remote.models

import com.myaxa.domain.movie_details.Actor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorDTO(
    @SerialName("id") val id: Long,
    @SerialName("photo") val photo: String? = null,
    @SerialName("name") val name: String? = null,
): InfoDTO {
    override fun toDomainModel() = Actor(
        id = id,
        name = name,
        photo = photo
    )
}