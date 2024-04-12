package com.myaxa.movie.details.details_items

import com.myaxa.movie.details.impl.R
import com.myaxa.movie.details.impl.databinding.ItemDescriptionBinding
import com.myaxa.movies.common.ViewBindingKotlinModel

internal data class DescriptionEpoxyModel(
    private val text: String,
) : ViewBindingKotlinModel<ItemDescriptionBinding>(R.layout.item_description) {

    override fun ItemDescriptionBinding.bind() {
        description.text = text
    }
}