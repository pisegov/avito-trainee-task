package com.myaxa.movies_catalog

data class Movie(
    val id: Long,
    val name: String?,
    val type: String,
    val year: Int?,
    val rating: Double? = null,
    val reviewCount: Int? = null,
    val ageRating: Int? = null,
    val poster: String? = null,
    //  val genres: List<String> = listOf(),
    //  val networks: List<String> = listOf(),
    //  val countries: List<String> = listOf(),
    //  val persons: List<String> = listOf(),
    // description
    // short description
)