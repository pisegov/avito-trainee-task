package com.myaxa.movie.details.models

import com.myaxa.domain.movie_details.Review
import com.myaxa.domain.movie_details.ReviewType
import com.myaxa.movie.details.impl.R

internal data class ReviewUI(
    val id: Long,
    val movieId: Long,
    val author: String,
    val title: String,
    val type: ReviewTypeUI,
    val review: String,
    val date: String,
) : AdditionalListItem

internal fun Review.toReviewUI() = ReviewUI(
    id = id,
    movieId = movieId,
    author = author,
    title = title,
    type = type.toReviewTypeUI(),
    review = review,
    date = date,
)

internal enum class ReviewTypeUI(val attr: Int) {
    POSITIVE(R.color.holo_green_dark),
    NEUTRAL(R.color.dark_grey),
    NEGATIVE(R.color.holo_red_dark),
}

internal fun ReviewType.toReviewTypeUI() =
    when (this) {
        ReviewType.POSITIVE -> ReviewTypeUI.POSITIVE
        ReviewType.NEUTRAL -> ReviewTypeUI.NEUTRAL
        ReviewType.NEGATIVE -> ReviewTypeUI.NEGATIVE
    }
