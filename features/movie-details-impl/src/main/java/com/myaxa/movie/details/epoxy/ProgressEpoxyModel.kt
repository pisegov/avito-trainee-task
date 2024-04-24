package com.myaxa.movie.details.epoxy

import com.myaxa.movie.details.impl.R
import com.myaxa.movie.details.impl.databinding.ItemProgressBinding
import com.myaxa.movies.common.ViewBindingKotlinModel

internal data class ProgressEpoxyModel(
    private val id: String,
) : ViewBindingKotlinModel<ItemProgressBinding>(R.layout.item_progress) {

    init {
        id(id)
    }

    override fun ItemProgressBinding.bind() {}
}