package com.myaxa.movies_catalog.ui.filters.bottomsheet.epoxy_controllers

import com.airbnb.epoxy.EpoxyController
import com.myaxa.movies_catalog.filters.Filters
import com.myaxa.movies_catalog.ui.filters.bottomsheet.epoxy_models.YearFilterEpoxyModel
import com.myaxa.movies_catalog.ui.filters.bottomsheet.epoxy_models.ListFilterEpoxyModel
import com.myaxa.movies_catalog.ui.filters.bottomsheet.epoxy_models.RatingFilterEpoxyModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class FiltersEpoxyController @Inject constructor(
    listEpoxyControllerFactory: ListFilterEpoxyController.Factory,
) : EpoxyController() {

    private val typesEpoxyController = listEpoxyControllerFactory.create { filter ->
        _filtersFlow.update { it?.copy(types = filter) }
    }

    private val countriesEpoxyController = listEpoxyControllerFactory.create { filter ->
        _filtersFlow.update { it?.copy(countries = filter) }
    }

    private val networksEpoxyController = listEpoxyControllerFactory.create { filter ->
        _filtersFlow.update { it?.copy(networks = filter) }
    }

    private val genresEpoxyController = listEpoxyControllerFactory.create { filter ->
        _filtersFlow.update { it?.copy(genres = filter) }
    }

    private val ageRatingsEpoxyController = listEpoxyControllerFactory.create { filter ->
        _filtersFlow.update { it?.copy(ageRatings = filter) }
    }

    private val _filtersFlow = MutableStateFlow<Filters?>(null)
    val filtersFlow = _filtersFlow.asStateFlow()

    fun updateFilters(filters: Filters?) {
        _filtersFlow.tryEmit(filters)
        requestModelBuild()
    }

    override fun buildModels() {
        filtersFlow.value?.let {
            YearFilterEpoxyModel(it.year) { filter ->
                _filtersFlow.update { it?.copy(year = filter) }
            }.id("year_filter").addTo(this)

            RatingFilterEpoxyModel(it.rating) { filter ->
                _filtersFlow.update { it?.copy(rating = filter) }
            }
                .id("rating_filter")
                .addTo(this)

            typesEpoxyController.filter = it.types
            ListFilterEpoxyModel(it.types.title, typesEpoxyController, 1)
                .id("types_filter").addTo(this)

            countriesEpoxyController.filter = it.countries
            ListFilterEpoxyModel(it.countries.title, countriesEpoxyController, 1)
                .id("countries_filter").addTo(this)

            networksEpoxyController.filter = it.networks
            ListFilterEpoxyModel(it.networks.title, networksEpoxyController, 1)
                .id("networks_filter").addTo(this)

            genresEpoxyController.filter = it.genres
            ListFilterEpoxyModel(it.genres.title, genresEpoxyController, 1)
                .id("genres_filter").addTo(this)

            ageRatingsEpoxyController.filter = it.ageRatings
            ListFilterEpoxyModel(it.ageRatings.title, ageRatingsEpoxyController, 1)
                .id("age_ratings_filter").addTo(this)
        }
    }
}