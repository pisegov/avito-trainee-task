package com.myaxa.movies_catalog.ui

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.myaxa.movies_catalog.MoviesCatalogViewModel
import com.myaxa.movies_catalog.models.MovieInCatalog
import javax.inject.Inject

class MoviesEpoxyController @Inject constructor(private val viewModel: MoviesCatalogViewModel) : PagingDataEpoxyController<MovieInCatalog>() {
    override fun buildItemModel(currentPosition: Int, item: MovieInCatalog?): EpoxyModel<*> {
        return MovieEpoxyModel(
            item!!,
            viewModel::onMovieClicked
        ).id("movie_${item.id}")
    }
}