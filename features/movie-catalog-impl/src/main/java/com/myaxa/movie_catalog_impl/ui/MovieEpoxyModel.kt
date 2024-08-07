package com.myaxa.movie_catalog_impl.ui

import android.view.View
import coil.load
import com.myaxa.movie_catalog_api.models.MovieInCatalog
import com.myaxa.movie_catalog_impl.R
import com.myaxa.movie_catalog_impl.databinding.ItemMovieBinding
import com.myaxa.movies.common.ViewBindingKotlinModel
import com.myaxa.movies.common.R as CommonR

internal data class MovieEpoxyModel(
    val model: MovieInCatalog,
    val onClick: (id: Long) -> Unit,
) : ViewBindingKotlinModel<ItemMovieBinding>(R.layout.item_movie) {

    override fun ItemMovieBinding.bind() {
        title.visibility = View.GONE
        image.load(model.poster) {
            placeholder(CommonR.drawable.movie_placeholder)
            error(CommonR.drawable.movie_placeholder)

            listener(
                onError = { _,_ ->
                    title.visibility = View.VISIBLE
                    title.text = model.name
                },
            )
        }

        image.setOnClickListener {
            onClick(model.id)
        }
    }
}