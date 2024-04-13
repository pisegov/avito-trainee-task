package com.myaxa.movies_catalog.ui.filters.bottomsheet.epoxy_controllers

import com.airbnb.epoxy.EpoxyController
import com.myaxa.movies_catalog.ui.filters.bottomsheet.epoxy_models.YearFilterEpoxyModel
import com.myaxa.movies_catalog.ui.filters.bottomsheet.epoxy_models.ListFilterEpoxyModel
import com.myaxa.movies_catalog.ui.filters.bottomsheet.epoxy_models.RatingFilterEpoxyModel
import javax.inject.Inject

class FiltersEpoxyController @Inject constructor(
    countriesEpoxyControllerFactory: ListFilterEpoxyController.Factory,
) : EpoxyController() {

    private val countriesEpoxyController = countriesEpoxyControllerFactory.create {filter ->
        updatedFilters = updatedFilters?.copy(countries = filter)
    }

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

            countriesEpoxyController.filter = it.countries
            ListFilterEpoxyModel(it.countries.title, countriesEpoxyController) { filter ->
                updatedFilters = updatedFilters?.copy(countries = filter)
                filters = updatedFilters
            }
                .id("countries_filter")
                .addTo(this)
        }
    }
}