package com.myaxa.movies.database.models

import com.myaxa.domain.movie_details.DetailsInfoModel

sealed interface MovieDetailsInfoDBO {
    fun toDomainModel(): DetailsInfoModel
}

sealed interface MovieDetailsInfoDBOCreator<in F : DetailsInfoModel, out T : MovieDetailsInfoDBO> {
    fun fromDomainModel(domainModel: F): T
}
