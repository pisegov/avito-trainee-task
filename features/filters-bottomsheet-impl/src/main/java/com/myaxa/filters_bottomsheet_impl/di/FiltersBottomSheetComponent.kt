package com.myaxa.filters_bottomsheet_impl.di

import com.myaxa.filters_bottomsheet_impl.ui.epoxy_controllers.FiltersEpoxyController
import com.myaxa.movie_catalog_api.MovieCatalogViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope

@Component
@FiltersBottomSheetScope
internal interface FiltersBottomSheetComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance viewModel: MovieCatalogViewModel,
        ): FiltersBottomSheetComponent
    }
    val filtersEpoxyController: FiltersEpoxyController
}

@Scope
annotation class FiltersBottomSheetScope