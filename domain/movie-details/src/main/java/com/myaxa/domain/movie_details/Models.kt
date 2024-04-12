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

data class Review(
    val id: Long,
    val movieId: Long,
    val author: String,
    val title: String,
    val type: ReviewType,
    val review: String,
    val date: String,
): DetailsInfoModel

enum class ReviewType {
    POSITIVE,
    NEUTRAL,
    NEGATIVE,
}