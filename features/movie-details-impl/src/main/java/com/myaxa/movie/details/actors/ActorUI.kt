package com.myaxa.movie.details.actors

import com.myaxa.domain.movie_details.Actor

data class ActorUI(
    val id: Long,
    val name: String? = null,
    val photo: String? = null,
)

fun Actor.toActorUI() = ActorUI(
    id = id,
    name = name,
    photo = photo,
)