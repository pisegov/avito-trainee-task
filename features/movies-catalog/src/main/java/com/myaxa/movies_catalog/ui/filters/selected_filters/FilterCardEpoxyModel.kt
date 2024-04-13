package com.myaxa.movies_catalog.ui.filters.selected_filters

import com.myaxa.movies.common.ViewBindingKotlinModel
import com.myaxa.movies_catalog.R
import com.myaxa.movies_catalog.databinding.ItemSelectedFilterCardBinding

data class FilterCardEpoxyModel(
    private val model: FilterCard,
    private val onClick: (FilterCard) -> Unit,
) : ViewBindingKotlinModel<ItemSelectedFilterCardBinding>(R.layout.item_selected_filter_card) {
    override fun ItemSelectedFilterCardBinding.bind() {
        name.text = model.content
        name.setOnClickListener {
            onClick(model)
        }
    }
}