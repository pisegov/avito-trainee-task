package com.myaxa.movie.details.models

import com.myaxa.domain.movie_details.Actor

internal data class ActorUI(
    val id: Long,
    val name: String? = null,
    val photo: String? = null,
) : AdditionalListItem

internal fun Actor.toActorUI() = ActorUI(
    id = id,
    name = name,
    photo = photo,
)