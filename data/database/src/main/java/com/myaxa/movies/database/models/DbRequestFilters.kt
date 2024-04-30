package com.myaxa.movies.database.models

data class DbRequestFilters(
    val yearFrom: Int? = null,
    val yearTo: Int? = null,
    val yearCertain: Int? = null,
    val rating: Double? = null,
    val countries: List<String>? = null,
    val types: List<String>? = null,
    val networks: List<String>? = null,
    val genres: List<String>? = null,
    val ageRatings: List<String>? = null,
)