package com.myaxa.movies_api.models

import kotlinx.serialization.Serializable

@Serializable
data class ResponseDTO(
    val docs: List<MovieDTO>,
)