package com.myaxa.movie.details

import androidx.paging.LoadState
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel
import com.myaxa.movie.details.details_items.ActorsEpoxyController
import com.myaxa.movie.details.details_items.ActorsListEpoxyModel
import com.myaxa.movie.details.details_items.CardsListEpoxyModel
import com.myaxa.movie.details.details_items.DescriptionEpoxyModel
import com.myaxa.movie.details.details_items.EpisodesEpoxyController
import com.myaxa.movie.details.details_items.ImagesEpoxyController
import com.myaxa.movie.details.details_items.ProgressEpoxyModel
import com.myaxa.movie.details.details_items.ReviewsEpoxyController
import com.myaxa.movie.details.details_items.PlaceholderEpoxyModel
import com.myaxa.movie.details.models.MovieDetailsUI
import javax.inject.Inject

internal class DetailsEpoxyController @Inject constructor(
    val actorsEpoxyController: ActorsEpoxyController,
    val reviewsEpoxyController: ReviewsEpoxyController,
    val imagesEpoxyController: ImagesEpoxyController,
    val episodesEpoxyController: EpisodesEpoxyController,
) : EpoxyController() {

    var model: MovieDetailsUI? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    var actorsLoadingState: LoadState = LoadState.Loading
        set(value) {
            field = value; requestModelBuild()
        }

    var reviewsLoadingState: LoadState = LoadState.Loading
        set(value) {
            field = value; requestModelBuild()
        }
    var imagesLoadingState: LoadState = LoadState.Loading
        set(value) {
            field = value; requestModelBuild()
        }
    var episodesLoadingState: LoadState = LoadState.Loading
        set(value) {
            field = value; requestModelBuild()
        }


    override fun buildModels() {
        model?.run {

            (description?.let {
                DescriptionEpoxyModel(it)
            } ?: PlaceholderEpoxyModel("Нет описания"))
                .id("movie_description")
                .addTo(this@DetailsEpoxyController)


            addListEpoxyModel(
                model = ActorsListEpoxyModel(actorsEpoxyController),
                modelId = "actors_list",
                loadingState = actorsLoadingState,
                placeholderText = "Нет информации об актерах"
            )

            addListEpoxyModel(
                model = CardsListEpoxyModel(reviewsEpoxyController, "Отзывы"),
                modelId = "reviews_list",
                loadingState = reviewsLoadingState,
                placeholderText = "Нет отзывов"
            )

            addListEpoxyModel(
                model = CardsListEpoxyModel(imagesEpoxyController, "Изображения"),
                modelId = "images_list",
                loadingState = imagesLoadingState,
                placeholderText = "Нет изображений"
            )

            if (isSeries) {
                addListEpoxyModel(
                    model = CardsListEpoxyModel(episodesEpoxyController, "Эпизоды"),
                    modelId = "episodes_list",
                    loadingState = episodesLoadingState,
                    placeholderText = "Нет эпизодов"
                )
            }
        }
    }

    private fun addListEpoxyModel(
        model: EpoxyModel<*>,
        modelId: String,
        loadingState: LoadState,
        placeholderText: String,
    ) {
        when (loadingState) {
            is LoadState.NotLoading -> {
                model
            }

            is LoadState.Loading -> {
                ProgressEpoxyModel()
            }

            else -> {
                PlaceholderEpoxyModel(placeholderText)
            }
        }
            .id(modelId)
            .addTo(this)
    }
}
