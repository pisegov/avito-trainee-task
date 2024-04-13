package com.myaxa.movie.details.details_items

import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.myaxa.movie.details.impl.R
import com.myaxa.movie.details.impl.databinding.ItemCardsListBinding
import com.myaxa.movies.common.ViewBindingKotlinModel

internal data class CardsListEpoxyModel(
    private val controller: PagingDataEpoxyController<*>,
    private val titleText: String,
) : ViewBindingKotlinModel<ItemCardsListBinding>(R.layout.item_cards_list) {

    override fun ItemCardsListBinding.bind() {
        title.text = titleText
        val context = this.root.context
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        list.setController(controller)
        list.layoutManager = layoutManager
    }
}