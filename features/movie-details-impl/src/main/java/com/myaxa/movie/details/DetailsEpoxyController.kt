package com.myaxa.movie.details

import com.airbnb.epoxy.EpoxyController
import com.myaxa.movie.details.details_items.ActorsEpoxyController
import com.myaxa.movie.details.details_items.ActorsListEpoxyModel
import com.myaxa.movie.details.details_items.DescriptionEpoxyModel
import com.myaxa.movie.details.details_items.EpisodesEpoxyController
import com.myaxa.movie.details.details_items.EpisodesListEpoxyModel
import com.myaxa.movie.details.details_items.ImagesEpoxyController
import com.myaxa.movie.details.details_items.ImagesListEpoxyModel
import com.myaxa.movie.details.details_items.ReviewsEpoxyController
import com.myaxa.movie.details.details_items.ReviewsListEpoxyModel
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

    override fun buildModels() {
        model?.run {
            description?.let {
                DescriptionEpoxyModel(it)
                    .id("movie_description")
                    .addTo(this@DetailsEpoxyController)
            }

            ActorsListEpoxyModel(actorsEpoxyController)
                .id("actors_list")
                .addTo(this@DetailsEpoxyController)

            ReviewsListEpoxyModel(reviewsEpoxyController)
                .id("reviews_list")
                .addTo(this@DetailsEpoxyController)

            ImagesListEpoxyModel(imagesEpoxyController)
                .id("images_list")
                .addTo(this@DetailsEpoxyController)

            if (isSeries) {
                EpisodesListEpoxyModel(episodesEpoxyController)
                    .id("episodes_list")
                    .addTo(this@DetailsEpoxyController)
            }
        }
    }
}

