package com.myaxa.data.actors_remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorDTO(
    @SerialName("id") var id: Long,
    @SerialName("photo") var photo: String? = null,
    @SerialName("name") var name: String? = null,
)


@Serializable
data class ActorsResponseDTO(
    @SerialName("docs") val actors: List<ActorDTO>,
    @SerialName("limit") val limit: Int,
    @SerialName("page") val page: Int,
    @SerialName("pages") val pages: Int,
)

