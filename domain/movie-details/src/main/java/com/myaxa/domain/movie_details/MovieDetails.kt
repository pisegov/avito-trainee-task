package com.myaxa.domain.movie_details

data class MovieDetails (
    val id: Long,
    val name: String?,
    val type: String,
    val year: Int?,
    val rating: Double? = null,
    val reviewCount: Int? = null,
    val ageRating: Int? = null,
    val poster: String? = null,
    val backdrop: String? = null,
    val description: String? = null,
    val shortDescription: String? = null,
    val isSeries: Boolean,
    //  val genres: List<String> = listOf(),
    //  val networks: List<String> = listOf(),
    //  val countries: List<String> = listOf(),
    //  val persons: List<String> = listOf(),
    // description
    // short description
)
