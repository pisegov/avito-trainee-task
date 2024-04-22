package com.myaxa.movie_catalog_impl.ui

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.myaxa.movie_catalog_api.models.MovieInCatalog
import javax.inject.Inject

internal class MovieCatalogEpoxyController @Inject constructor(
    private val navigationCallback: (id: Long) -> Unit,
) : PagingDataEpoxyController<MovieInCatalog>() {
    override fun buildItemModel(currentPosition: Int, item: MovieInCatalog?): EpoxyModel<*> {

        return MovieEpoxyModel(item!!, ::navigationCallback.get())
            .id("movie_${item.id}")
    }
}