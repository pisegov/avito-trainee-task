package com.myaxa.movie.details

import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.epoxy.EpoxyController
import com.myaxa.movie.details.actors.ActorsEpoxyController
import com.myaxa.movie.details.impl.R
import com.myaxa.movie.details.impl.databinding.ItemActorsListBinding
import com.myaxa.movie.details.impl.databinding.ItemDescriptionBinding
import com.myaxa.movies.common.ViewBindingKotlinModel
import javax.inject.Inject

internal class DetailsEpoxyController @Inject constructor(
    val actorsEpoxyController: ActorsEpoxyController,
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
        }
    }
}

internal data class DescriptionEpoxyModel(
    private val text: String,
) : ViewBindingKotlinModel<ItemDescriptionBinding>(R.layout.item_description) {

    override fun ItemDescriptionBinding.bind() {
        description.text = text
    }
}

internal data class ActorsListEpoxyModel(
    private val actorsEpoxyController: ActorsEpoxyController,
) : ViewBindingKotlinModel<ItemActorsListBinding>(R.layout.item_actors_list) {

    override fun ItemActorsListBinding.bind() {
        val context = this.root.context
        val layoutManager = GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
        list.setController(actorsEpoxyController)
        list.layoutManager = layoutManager
    }
}