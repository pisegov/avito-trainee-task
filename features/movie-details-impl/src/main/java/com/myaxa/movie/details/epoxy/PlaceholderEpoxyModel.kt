package com.myaxa.movie.details.epoxy

import com.myaxa.movie.details.impl.R
import com.myaxa.movie.details.impl.databinding.ItemPlaceholderBinding
import com.myaxa.movie.details.models.DetailsItemConfig
import com.myaxa.movies.common.ViewBindingKotlinModel

internal data class PlaceholderEpoxyModel(
    val listInfo: DetailsItemConfig,
) : ViewBindingKotlinModel<ItemPlaceholderBinding>(R.layout.item_placeholder) {

    init {
        id(listInfo.id)
    }

    override fun ItemPlaceholderBinding.bind() {
        text.text = listInfo.placeholderText
    }
}