package com.myaxa.data.actors_remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDTO<T: InfoDTO>(
    @SerialName("docs") val list: List<T>,
    @SerialName("limit") val limit: Int,
    @SerialName("page") val page: Int,
    @SerialName("pages") val pages: Int,
)

