package com.myaxa.data.mappers

import com.myaxa.data.actors_remote.ActorDTO
import com.myaxa.domain.movie_details.Actor
import com.myaxa.movies.database.models.MovieDBO
import com.myaxa.movies_api.models.MovieDTO

fun MovieDTO.toMovieDBO() = MovieDBO(
    id = id,
    name = name,
    type = type,
    year = year,
    description = description,
    shortDescription = shortDescription,
    rating = rating?.kp,
    ageRating = ageRating,
    poster = poster?.previewUrl,
    backdrop = backdrop?.previewUrl,
    reviewCount = reviewInfo?.count,
    isSeries = isSeries,
)

fun ActorDTO.toActor() = Actor(
    id = id,
    name = name,
    photo = photo
)