package com.myaxa.movies_catalog.models

data class MovieInCatalog(
    val id: Long,
    val name: String,
    val type: String,
    val typeNumber: Int,
    val year: Int,
    val rating: Double? = null,
    val votes: Long? = null,
    val ageRating: Int? = null,
    val logoUrl: String? = null,
    val poster: String? = null,
    val backdrop: String? = null,
    //  val genres: List<String> = listOf(),
    //  val persons: List<Persons> = listOf(),
    val reviewCount: Int? = null,
    //  val seasonsInfo: List<SeasonsInfo> = listOf(),
    //  val totalSeriesLength: Int? = null,
    //  val seriesLength: Int? = null,
    val isSeries: Boolean,
    //  val networks: Networks? = Networks(),
)

