package com.myaxa.movies_api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Votes(
    @SerialName("kp") val kp: Long? = null,
)