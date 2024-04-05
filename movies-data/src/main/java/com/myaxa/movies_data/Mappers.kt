package com.myaxa.movies_data

import com.myaxa.movies.database.models.MovieDBO
import com.myaxa.movies_api.models.MovieDTO

internal fun MovieDTO.toMovieDBO() = MovieDBO(
    id = id,
    name = name,
    type = type,
    typeNumber = typeNumber,
    year = year,
    description = description,
    shortDescription = shortDescription,
    slogan = slogan,
    rating = rating?.kp,
    votes = votes?.kp,
    ageRating = ageRating,
    logoUrl = logo?.url,
    poster = poster?.previewUrl,
    backdrop = backdrop?.previewUrl,
    reviewCount = reviewInfo?.count,
    isSeries = isSeries,
)

internal fun MovieDBO.toMovie() = Movie(
    id = id,
    name = name,
    type = type,
    typeNumber = typeNumber,
    year = year,
    description = description,
    shortDescription = shortDescription,
    slogan = slogan,
    rating = rating,
    votes = votes,
    ageRating = ageRating,
    logoUrl = logoUrl,
    poster = poster,
    backdrop = backdrop,
    reviewCount = reviewCount,
    isSeries = isSeries,
)