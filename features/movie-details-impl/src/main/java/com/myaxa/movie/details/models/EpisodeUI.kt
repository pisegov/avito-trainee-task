package com.myaxa.movie.details.models

import com.myaxa.domain.movie_details.Episode

data class EpisodeUI(
    val seasonNumber: Int,
    val episodeNumber: Int,
    val title: String,
    val date: String,
    val image: String? = null,
): AdditionalListItem

fun Episode.toEpisodeUI() = EpisodeUI(
    seasonNumber = seasonNumber,
    episodeNumber = episodeNumber,
    title = title,
    date = date,
    image = image,
)