package com.myaxa.movies_catalog.ui.filters

import com.airbnb.epoxy.EpoxyController
import com.myaxa.movies.common.ViewBindingKotlinModel
import com.myaxa.movies_catalog.MoviesCatalogViewModel
import com.myaxa.movies_catalog.R
import com.myaxa.movies_catalog.databinding.ItemSelectedFilterCardBinding
import com.myaxa.movies_data.Filter
import com.myaxa.movies_data.FilterType
import com.myaxa.movies_data.Filters
import javax.inject.Inject

class ChosenFiltersEpoxyController @Inject constructor(
    private val viewModel: MoviesCatalogViewModel,
) : EpoxyController() {

    var filters: Filters? = null
        set(value) {
            field = value

            val list = mutableListOf<FilterCard>()

            value?.run {
                map.keys.forEach { type ->
                    map[type]?.let {
                        when (it) {
                            is Filter.ListFilter -> {
                                if (it.isSelected) {
                                    list.addAll(
                                        it.options.keys.map { key -> FilterCard(type, key) }
                                    )
                                }
                            }

                            else -> {
                                if (it.isSelected) {
                                    list.add(
                                        FilterCard(type, it.toString())
                                    )
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
                        map[card.content] = false
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

data class FilterCard(
    val type: FilterType,
    val content: String,
)

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