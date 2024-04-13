package com.myaxa.movies_catalog.ui.filters.bottomsheet.epoxy_models

import com.myaxa.movies.common.ViewBindingKotlinModel
import com.myaxa.movies_catalog.filters.FilterValue
import com.myaxa.movies_catalog.R
import com.myaxa.movies_catalog.databinding.ItemFilterListOptionBinding

data class ListFilterOptionEpoxyModel(
    val filterKey: String,
    val filterValue: FilterValue,
    val updateFilter: (String, FilterValue) -> Unit,
) : ViewBindingKotlinModel<ItemFilterListOptionBinding>(R.layout.item_filter_list_option) {
    override fun ItemFilterListOptionBinding.bind() {
        text.text = filterValue.title
        container.isSelected = filterValue.isSelected

        container.setOnClickListener { updateFilter(filterKey, filterValue.copy(isSelected = !filterValue.isSelected)) }
    }
}