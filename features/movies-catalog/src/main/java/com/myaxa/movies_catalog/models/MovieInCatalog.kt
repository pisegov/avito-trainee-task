package com.myaxa.movies_catalog.models

data class MovieInCatalog(
    val id: Long,
    val name: String?,
    val type: String,
    val year: Int?,
    val rating: Double? = null,
    val reviewCount: Int? = null,
    val ageRating: Int? = null,
    val poster: String? = null,
    //  val genres: String? = null,
)

