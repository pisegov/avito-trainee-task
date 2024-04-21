package com.myaxa.movie_catalog_impl.ui.filters

import com.myaxa.movie_catalog_impl.filters.FilterType

internal data class FilterCard(
    val type: FilterType,
    val key: String,
    val content: String,
)