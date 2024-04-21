package com.myaxa.movies_catalog.ui

import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel

class CheckIfListIsEmptyInterceptor(private val action: (Boolean) -> Unit) : EpoxyController.Interceptor {
    override fun intercept(models: MutableList<EpoxyModel<*>>) {
        action(models.isEmpty())
    }
}