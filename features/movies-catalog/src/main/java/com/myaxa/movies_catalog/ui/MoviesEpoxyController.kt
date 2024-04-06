package com.myaxa.movies_catalog.ui

import com.airbnb.epoxy.EpoxyController
import com.myaxa.movies_catalog.MoviesCatalogViewModel
import com.myaxa.movies_catalog.models.MovieInCatalog
import javax.inject.Inject

class MoviesEpoxyController @Inject constructor(private val viewModel: MoviesCatalogViewModel) : EpoxyController() {

    var movies: List<MovieInCatalog> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        movies.forEach { model ->
            MovieEpoxyModel("movie_${model.id}", model, viewModel::onMovieClicked).addTo(this)
        }
    }
}