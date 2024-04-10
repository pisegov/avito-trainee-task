package com.myaxa.movies_catalog.ui

import coil.load
import com.myaxa.movies.common.ViewBindingKotlinModel
import com.myaxa.movies_catalog.R
import com.myaxa.movies_catalog.databinding.ItemMovieBinding
import com.myaxa.movies_catalog.models.MovieInCatalog

data class MovieEpoxyModel(
    val model: MovieInCatalog,
    val onClick: (id: Long) -> Unit,
) : ViewBindingKotlinModel<ItemMovieBinding>(R.layout.item_movie) {

    override fun ItemMovieBinding.bind() {
        image.load(model.poster) {
            placeholder(R.drawable.movie_placeholder)
            error(R.drawable.movie_placeholder)
        }

        image.setOnClickListener {
            onClick(model.id)
        }
    }
}