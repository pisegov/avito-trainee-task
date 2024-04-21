package com.myaxa.movie_catalog_impl.ui.filters

import com.myaxa.movie_catalog_impl.R
import com.myaxa.movie_catalog_impl.databinding.ItemSelectedFilterCardBinding
import com.myaxa.movies.common.ViewBindingKotlinModel

internal data class FilterCardEpoxyModel(
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