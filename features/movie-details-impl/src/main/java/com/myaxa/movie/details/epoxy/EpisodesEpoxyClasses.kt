package com.myaxa.movie.details.epoxy

import coil.load
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.myaxa.movie.details.impl.R
import com.myaxa.movie.details.impl.databinding.ItemEpisodeBinding
import com.myaxa.movie.details.models.EpisodeUI
import com.myaxa.movies.common.ViewBindingKotlinModel
import javax.inject.Inject
import com.myaxa.movies.common.R as CommonR

internal class EpisodesEpoxyController @Inject constructor() : PagingDataEpoxyController<EpisodeUI>() {
    override fun buildItemModel(currentPosition: Int, item: EpisodeUI?): EpoxyModel<*> {
        return EpisodeEpoxyModel(item!!)
            .id("s${item.seasonNumber}_e${item.episodeNumber}")
    }
}

internal data class EpisodeEpoxyModel(private val model: EpisodeUI) :
    ViewBindingKotlinModel<ItemEpisodeBinding>(R.layout.item_episode) {

    override fun ItemEpisodeBinding.bind() {
        title.text = model.title
        season.text = "Сезон ${model.seasonNumber}"
        episode.text = "Серия ${model.episodeNumber}"
        date.text = model.date
        image.load(model.image) {
            placeholder(CommonR.drawable.movie_placeholder)
            error(CommonR.drawable.movie_placeholder)
        }
    }
}

