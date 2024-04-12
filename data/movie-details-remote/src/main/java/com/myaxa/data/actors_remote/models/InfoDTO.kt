package com.myaxa.data.actors_remote.models

import com.myaxa.domain.movie_details.DetailsInfoModel

sealed interface InfoDTO {
    fun toDomainModel(): DetailsInfoModel
}

