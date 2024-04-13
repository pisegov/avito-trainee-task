package com.myaxa.movies_catalog.ui.filters.selected_filters

import com.myaxa.movies_catalog.FilterType

data class FilterCard(
    val type: FilterType,
    val content: String,
)