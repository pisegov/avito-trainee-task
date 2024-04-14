package com.myaxa.domain.movie_details

data class MovieDetails (
    val id: Long,
    val name: String?,
    val year: Int?,
    val rating: Double? = null,
    val reviewCount: Int? = null,
    val ageRating: Int? = null,
    val poster: String? = null,
    val backdrop: String? = null,
    val description: String? = null,
    val isSeries: Boolean,
)
