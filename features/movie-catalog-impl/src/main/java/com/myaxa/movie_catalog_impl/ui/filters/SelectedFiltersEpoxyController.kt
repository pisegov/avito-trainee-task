package com.myaxa.movie_catalog_impl.ui.filters

import com.airbnb.epoxy.EpoxyController
import com.myaxa.movie_catalog_api.FiltersStateHolder
import com.myaxa.movie_catalog_impl.filters.Filter
import com.myaxa.movie_catalog_impl.filters.FilterValue
import com.myaxa.movie_catalog_impl.filters.Filters
import javax.inject.Inject

internal class SelectedFiltersEpoxyController @Inject constructor(
    private val filtersStateHolder: FiltersStateHolder,
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
                    filtersStateHolder.updateFilters(filters?.copyWithReplacedFilter(type = card.type, filter = updatedFilter))
                }
            }
                .id("filter_${card}")
                .addTo(this)
        }
    }
}
