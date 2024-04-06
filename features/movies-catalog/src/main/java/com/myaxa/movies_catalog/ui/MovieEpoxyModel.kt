package com.myaxa.movies_catalog.ui

import coil.load
import com.myaxa.movies.common.ViewBindingKotlinModel
import com.myaxa.movies_catalog.R
import com.myaxa.movies_catalog.databinding.ItemMovieBinding
import com.myaxa.movies_catalog.models.MovieInCatalog
import com.myaxa.movies.common.R as CommonR

data class MovieEpoxyModel(
    val id: String,
    val model: MovieInCatalog,
    val onClick: (id: Long) -> Unit,
) : ViewBindingKotlinModel<ItemMovieBinding>(R.layout.item_movie) {

    init {
        id(id)
    }

    override fun ItemMovieBinding.bind() {
        image.load(model.poster) {
            placeholder(CommonR.color.black)
        }

        image.setOnClickListener {
            onClick(model.id)
        }
    }
}