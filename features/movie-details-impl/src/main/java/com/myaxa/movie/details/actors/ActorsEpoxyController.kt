package com.myaxa.movie.details.actors

import android.util.Log
import coil.load
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.myaxa.movie.details.impl.R
import com.myaxa.movie.details.impl.databinding.ItemActorBinding
import com.myaxa.movies.common.ViewBindingKotlinModel
import javax.inject.Inject

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