package com.myaxa.movie.details.models

import com.myaxa.domain.movie_details.Image

internal data class ImageUI(
    val movieId: Long,
    val url: String,
): AdditionalListItem

internal fun Image.toImageUI() = ImageUI(
    movieId = movieId,
    url = url,
)