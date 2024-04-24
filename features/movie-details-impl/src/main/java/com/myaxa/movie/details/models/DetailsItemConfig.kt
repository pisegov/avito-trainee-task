package com.myaxa.movie.details.models

import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.myaxa.movie.details.epoxy.ActorsEpoxyController
import com.myaxa.movie.details.epoxy.EpisodesEpoxyController
import com.myaxa.movie.details.epoxy.ImagesEpoxyController
import com.myaxa.movie.details.epoxy.ReviewsEpoxyController
import javax.inject.Inject

/*
* Parameters for each details list epoxy model
* */
internal sealed interface DetailsItemConfig {
    val id: String
    val titleText: String
    val placeholderText: String

    data object DescriptionConfig : DetailsItemConfig {
        override val id: String = "movie_description"
        override val titleText: String = "Описание"
        override val placeholderText: String = "Нет описания"
    }
}

internal sealed interface AdditionalListConfig : DetailsItemConfig {
    val controller: PagingDataEpoxyController<out AdditionalListItem>
    val recycledViewPool: RecycledViewPool

    data class ActorsListConfig @Inject constructor(
        override val controller: ActorsEpoxyController,
        override val recycledViewPool: RecycledViewPool,
    ) : AdditionalListConfig {
        override val id: String = "actors_list"
        override val titleText: String = "Актеры"
        override val placeholderText: String = "Нет информации об актерах"
    }

    data class ReviewsListConfig @Inject constructor(
        override val controller: ReviewsEpoxyController,
        override val recycledViewPool: RecycledViewPool,
    ) : AdditionalListConfig {
        override val id: String = "reviews_list"
        override val titleText: String = "Отзывы"
        override val placeholderText: String = "Нет отзывов"
    }

    data class ImagesListConfig @Inject constructor(
        override val controller: ImagesEpoxyController,
        override val recycledViewPool: RecycledViewPool,
    ) : AdditionalListConfig {
        override val id: String = "images_list"
        override val titleText: String = "Изображения"
        override val placeholderText: String = "Нет изображений"
    }

    data class EpisodeListConfig @Inject constructor(
        override val controller: EpisodesEpoxyController,
        override val recycledViewPool: RecycledViewPool,
    ) : AdditionalListConfig {
        override val id: String = "episodes_list"
        override val titleText: String = "Эпизоды"
        override val placeholderText: String = "Нет эпизодов"
    }
}