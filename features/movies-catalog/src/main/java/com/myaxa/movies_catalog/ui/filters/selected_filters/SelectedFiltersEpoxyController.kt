package com.myaxa.movies_catalog.ui.filters.selected_filters

import com.airbnb.epoxy.EpoxyController
import com.myaxa.movies_catalog.filters.Filter
import com.myaxa.movies_catalog.filters.FilterValue
import com.myaxa.movies_catalog.filters.Filters
import com.myaxa.movies_catalog.MoviesCatalogViewModel
import javax.inject.Inject

class SelectedFiltersEpoxyController @Inject constructor(
    private val viewModel: MoviesCatalogViewModel,
) : EpoxyController() {

    var filters: Filters? = null
        set(value) {
            field = value

            val list = mutableListOf<FilterCard>()

            value?.run {
                map.keys.forEach { type ->
                    map[type]?.let { filter ->
                        when (filter) {
                            is Filter.ListFilter -> {
                                list.addAll(
                                    filter.options.filter {
                                        it.value.isSelected
                                    }.map { (key, value) -> FilterCard(type, key, value.title) }
                                )
                            }

                            else -> {
                                if (filter.isSelected) {
                                    val filterString = filter.toString()
                                    list.add(FilterCard(type, filterString, filterString))
                                }
                            }
                        }
                    }
                }
            }

            filtersList = list
        }

    private var filtersList: List<FilterCard> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        filtersList.forEach { card ->
            FilterCardEpoxyModel(card) {

                val filter = filters?.map?.get(card.type)

                val updatedFilter = filter?.let {
                    if (it is Filter.ListFilter) {
                        val map = it.options.toMutableMap()
                        map[card.key] = FilterValue(card.content, false)
                        it.copy(options = map)
                    }
                    else {
                        it.clearedSelectionCopy()
                    }
                }

                updatedFilter?.let {
                    viewModel.updateFilters(filters?.copyWithReplacedFilter(type = card.type, filter = updatedFilter))
                }
            }
                .id("filter_${card}")
                .addTo(this)
        }
    }
}

