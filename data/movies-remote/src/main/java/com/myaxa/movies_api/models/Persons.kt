package com.myaxa.movies_api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Persons(
    @SerialName("id") var id: Int? = null,
    @SerialName("photo") var photo: String? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("description") var description: String? = null,
    @SerialName("profession") var profession: String? = null,
)