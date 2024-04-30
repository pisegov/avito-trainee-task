package com.myaxa.movies_api.models

data class NetworkRequestFilters(
    val year: String? = null,
    val rating: String? = null,
    val countries: List<String>? = null,
    val types: List<String>? = null,
    val networks: List<String>? = null,
    val genres: List<String>? = null,
    val ageRatings: List<String>? = null,
)