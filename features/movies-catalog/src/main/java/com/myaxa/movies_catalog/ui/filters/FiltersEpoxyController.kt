package com.myaxa.movies_catalog.ui.filters

import com.airbnb.epoxy.EpoxyController
import com.myaxa.movies_catalog.Filters
import javax.inject.Inject

class FiltersEpoxyController @Inject constructor() : EpoxyController() {
    var filters: com.myaxa.movies_catalog.Filters? = null
        set(value) {
            field = value
            updatedFilters = value
            if (field != null) {
                requestModelBuild()
            }
        }

    var updatedFilters = filters

    override fun buildModels() {
        filters?.let {
            YearFilterEpoxyModel(it.year) { filter ->
                updatedFilters = updatedFilters?.copy(year = filter)
            }.id("year_filter").addTo(this)

            RatingFilterEpoxyModel(it.rating) { filter ->
                updatedFilters = updatedFilters?.copy(rating = filter)
            }
                .id("rating_filter")
                .addTo(this)
        }
    }
}