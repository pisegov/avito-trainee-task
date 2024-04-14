package com.myaxa.data.actors_remote.models

import com.myaxa.domain.movie_details.DetailsInfoModel

/*
* Интерфейс для DTO моделей дополнительных деталей фильма
* Нужен для обобщения логики пагинации в списках
* */
sealed interface InfoDTO {
    fun toDomainModel(): DetailsInfoModel
}

