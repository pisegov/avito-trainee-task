package com.myaxa.movie.details.details_items

import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.myaxa.movie.details.impl.R
import com.myaxa.movie.details.impl.databinding.ItemActorBinding
import com.myaxa.movie.details.impl.databinding.ItemActorsListBinding
import com.myaxa.movie.details.models.ActorUI
import com.myaxa.movies.common.ViewBindingKotlinModel
import javax.inject.Inject

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

internal class ActorsEpoxyController @Inject constructor() : PagingDataEpoxyController<ActorUI>() {
    override fun buildItemModel(currentPosition: Int, item: ActorUI?): EpoxyModel<*> {
        return ActorEpoxyModel(item!!)
            .id("actor_${item.id}")
    }
}

internal data class ActorEpoxyModel(private val model: ActorUI) :
    ViewBindingKotlinModel<ItemActorBinding>(R.layout.item_actor) {

    override fun ItemActorBinding.bind() {
        image.load(model.photo) {
            placeholder(R.drawable.actor_placeholder)
            error(R.drawable.actor_placeholder)
        }
        name.text = model.name
    }
}