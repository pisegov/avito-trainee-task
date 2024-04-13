package com.myaxa.movies_catalog.ui.filters.bottomsheet.epoxy_models

import com.myaxa.movies.common.ViewBindingKotlinModel
import com.myaxa.movies_catalog.R
import com.myaxa.movies_catalog.databinding.ItemFilterListOptionBinding

data class ListFilterOptionEpoxyModel(
    val filterOption: Pair<String, Boolean>,
    val updateFilter: (Pair<String, Boolean>) -> Unit,
) : ViewBindingKotlinModel<ItemFilterListOptionBinding>(R.layout.item_filter_list_option) {
    override fun ItemFilterListOptionBinding.bind() {
        text.text = filterOption.first
        container.isSelected = filterOption.second

        container.setOnClickListener { updateFilter(filterOption.first to !filterOption.second) }
    }
}