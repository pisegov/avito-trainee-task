package com.myaxa.movie.details.details_items

import com.myaxa.movie.details.impl.R
import com.myaxa.movie.details.impl.databinding.ItemPlaceholderBinding
import com.myaxa.movies.common.ViewBindingKotlinModel

data class PlaceholderEpoxyModel(val content: String) : ViewBindingKotlinModel<ItemPlaceholderBinding>(R.layout.item_placeholder) {
    override fun ItemPlaceholderBinding.bind() {
        text.text = content
    }
}