package com.myaxa.movies_catalog.ui.filters.selected_filters

import com.myaxa.movies_catalog.filters.FilterType

data class FilterCard(
    val type: FilterType,
    val key: String,
    val content: String,
)