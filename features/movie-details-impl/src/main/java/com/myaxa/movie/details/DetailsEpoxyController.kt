package com.myaxa.movie.details

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.paging.LoadState
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel
import com.myaxa.movie.details.di.MovieDetailsFragmentScope
import com.myaxa.movie.details.epoxy.ActorsListEpoxyModel
import com.myaxa.movie.details.epoxy.CardsListEpoxyModel
import com.myaxa.movie.details.epoxy.DescriptionEpoxyModel
import com.myaxa.movie.details.epoxy.PlaceholderEpoxyModel
import com.myaxa.movie.details.epoxy.ProgressEpoxyModel
import com.myaxa.movie.details.models.AdditionalListConfig
import com.myaxa.movie.details.models.AdditionalListConfig.ActorsListConfig
import com.myaxa.movie.details.models.AdditionalListConfig.EpisodeListConfig
import com.myaxa.movie.details.models.AdditionalListConfig.ImagesListConfig
import com.myaxa.movie.details.models.AdditionalListConfig.ReviewsListConfig
import com.myaxa.movie.details.models.DetailsItemConfig
import com.myaxa.movie.details.models.DetailsItemConfig.DescriptionConfig
import com.myaxa.movie.details.models.MovieDetailsUI
import kotlinx.coroutines.launch
import javax.inject.Inject

@MovieDetailsFragmentScope
internal class DetailsEpoxyController @Inject constructor(
    val actorsListConfig: ActorsListConfig,
    val reviewsListConfig: ReviewsListConfig,
    val imagesListConfig: ImagesListConfig,
    val episodeListConfig: EpisodeListConfig,
) : EpoxyController() {

    var model: MovieDetailsUI? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    private val loadStates: MutableMap<AdditionalListConfig, LoadState> = listOf(
        actorsListConfig, reviewsListConfig, imagesListConfig, episodeListConfig
    ).associateWith { LoadState.Loading }.toMutableMap()

    override fun buildModels() {
        model?.run {

            (description?.let {
                DescriptionEpoxyModel(DescriptionConfig, it)
            } ?: PlaceholderEpoxyModel(DescriptionConfig))
                .addTo(this@DetailsEpoxyController)

            addListEpoxyModel(ActorsListEpoxyModel(actorsListConfig), actorsListConfig)
            addListEpoxyModel(CardsListEpoxyModel(reviewsListConfig), reviewsListConfig)
            addListEpoxyModel(CardsListEpoxyModel(imagesListConfig), imagesListConfig)

            if (isSeries)
                addListEpoxyModel(CardsListEpoxyModel(episodeListConfig), episodeListConfig)

        }
    }

    suspend fun observeLoadingStates(viewLifecycleScope: LifecycleCoroutineScope) {
        viewLifecycleScope.launch {
            loadStates.keys.forEach { listInfo ->
                launch {
                    listInfo.controller.loadStateFlow.collect {
                        loadStates[listInfo] = it.source.refresh
                        requestModelBuild()
                    }
                }
            }
        }
    }

    private fun addListEpoxyModel(
        model: EpoxyModel<*>,
        info: DetailsItemConfig,
    ) {
        val state = loadStates[info] ?: return
        when (state) {
            is LoadState.NotLoading -> {
                model
            }

            is LoadState.Loading -> {
                ProgressEpoxyModel(info.id)
            }

            else -> {
                PlaceholderEpoxyModel(info)
            }
        }
            .addTo(this)
    }
}
