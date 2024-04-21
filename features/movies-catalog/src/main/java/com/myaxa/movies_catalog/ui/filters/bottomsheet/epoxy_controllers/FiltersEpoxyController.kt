package com.myaxa.movies_catalog.ui.filters.bottomsheet.epoxy_controllers

import com.airbnb.epoxy.EpoxyController
import com.myaxa.movies_catalog.filters.Filters
import com.myaxa.movies_catalog.ui.filters.bottomsheet.epoxy_models.ListFilterEpoxyModel
import com.myaxa.movies_catalog.ui.filters.bottomsheet.epoxy_models.RatingFilterEpoxyModel
import com.myaxa.movies_catalog.ui.filters.bottomsheet.epoxy_models.YearFilterEpoxyModel
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

    fun clearFilters() {
        _filtersFlow.update { it?.clearedCopy() }
        requestModelBuild()
    }

    override fun buildModels() {
        filtersFlow.value?.let { filters ->
            YearFilterEpoxyModel(filters.year) { filter ->
                _filtersFlow.update { it?.copy(year = filter) }
            }.id("year_filter").addTo(this)

            RatingFilterEpoxyModel(filters.rating) { filter ->
                _filtersFlow.update { it?.copy(rating = filter) }
            }
                .id("rating_filter")
                .addTo(this)

            typesEpoxyController.filter = filters.types
            ListFilterEpoxyModel(filters.types.title, typesEpoxyController, 1)
                .id("types_filter").addTo(this)

            countriesEpoxyController.filter = filters.countries
            ListFilterEpoxyModel(filters.countries.title, countriesEpoxyController, 3)
                .id("countries_filter").addTo(this)

            networksEpoxyController.filter = filters.networks
            ListFilterEpoxyModel(filters.networks.title, networksEpoxyController, 1)
                .id("networks_filter").addTo(this)

            genresEpoxyController.filter = filters.genres
            ListFilterEpoxyModel(filters.genres.title, genresEpoxyController, 2)
                .id("genres_filter").addTo(this)

            ageRatingsEpoxyController.filter = filters.ageRatings
            ListFilterEpoxyModel(filters.ageRatings.title, ageRatingsEpoxyController, 1)
                .id("age_ratings_filter").addTo(this)
        }
    }
}