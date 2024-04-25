package com.myaxa.filters_bottomsheet_impl.ui.epoxy_models

import com.myaxa.filters_bottomsheet_impl.R
import com.myaxa.filters_bottomsheet_impl.databinding.ItemFilterListOptionBinding
import com.myaxa.movie_catalog_impl.filters.FilterValue
import com.myaxa.movies.common.ViewBindingKotlinModel

internal data class ListFilterOptionEpoxyModel(
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