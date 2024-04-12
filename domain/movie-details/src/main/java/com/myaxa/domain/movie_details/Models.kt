package com.myaxa.domain.movie_details

sealed interface DetailsInfoModel
data class Actor (
    val id: Long,
    val name: String? = null,
    val photo: String? = null,
): DetailsInfoModel

data class Image(
    val movieId: Long,
    val url: String,
): DetailsInfoModel
