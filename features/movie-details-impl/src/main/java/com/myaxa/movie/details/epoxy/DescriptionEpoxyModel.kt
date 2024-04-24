package com.myaxa.movie.details.epoxy

import com.myaxa.movie.details.impl.R
import com.myaxa.movie.details.impl.databinding.ItemDescriptionBinding
import com.myaxa.movie.details.models.DetailsItemConfig
import com.myaxa.movies.common.ViewBindingKotlinModel

internal data class DescriptionEpoxyModel(
    val itemInfo: DetailsItemConfig,
    private val text: String,
) : ViewBindingKotlinModel<ItemDescriptionBinding>(R.layout.item_description) {

    init {
        id(itemInfo.id)
    }

    override fun ItemDescriptionBinding.bind() {
        description.text = text
    }
}