package com.myaxa.movies.database.models

data class ActorDBO (
    val id: Long,
    val movieId: Long,
    val name: String? = null,
    val photo: String? = null,
)