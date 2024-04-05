package com.myaxa.movies_api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Items(
    @SerialName("name") var name: String? = null,
    @SerialName("logo") var logo: Logo? = Logo(),
)