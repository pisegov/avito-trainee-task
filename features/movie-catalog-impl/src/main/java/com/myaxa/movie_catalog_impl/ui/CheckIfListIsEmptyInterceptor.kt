package com.myaxa.movie_catalog_impl.ui

import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel

internal class CheckIfListIsEmptyInterceptor(private val action: (Boolean) -> Unit) : EpoxyController.Interceptor {
    override fun intercept(models: MutableList<EpoxyModel<*>>) {
        action(models.isEmpty())
    }
}