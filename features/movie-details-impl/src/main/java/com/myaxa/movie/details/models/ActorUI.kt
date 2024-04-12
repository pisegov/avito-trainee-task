package com.myaxa.movie.details.models

import com.myaxa.domain.movie_details.Actor

data class ActorUI(
    val id: Long,
    val name: String? = null,
    val photo: String? = null,
) : AdditionalListItem

fun Actor.toActorUI() = ActorUI(
    id = id,
    name = name,
    photo = photo,
)