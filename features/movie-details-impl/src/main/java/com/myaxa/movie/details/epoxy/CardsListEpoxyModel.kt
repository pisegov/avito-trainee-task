package com.myaxa.movie.details.epoxy

import androidx.recyclerview.widget.LinearLayoutManager
import com.myaxa.movie.details.impl.R
import com.myaxa.movie.details.impl.databinding.ItemCardsListBinding
import com.myaxa.movie.details.models.AdditionalListConfig
import com.myaxa.movies.common.ViewBindingKotlinModel

/*
 * A common model for lists of reviews, images and episodes
* */
internal data class CardsListEpoxyModel(
    private val config: AdditionalListConfig,
) : ViewBindingKotlinModel<ItemCardsListBinding>(R.layout.item_cards_list) {

    init {
        id(config.id)
    }

    override fun ItemCardsListBinding.bind() {
        title.text = config.titleText
        val context = this.root.context
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        list.layoutManager = layoutManager
        list.setController(config.controller)
        list.setRecycledViewPool(config.recycledViewPool)
    }
}