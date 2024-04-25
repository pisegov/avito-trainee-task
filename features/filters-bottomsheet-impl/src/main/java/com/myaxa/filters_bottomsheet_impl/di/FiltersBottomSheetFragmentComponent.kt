package com.myaxa.filters_bottomsheet_impl.di

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myaxa.filters_bottomsheet_impl.ui.epoxy_controllers.FiltersEpoxyController
import com.myaxa.movie_catalog_api.MovieCatalogViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope

@Component
@FiltersBottomSheetFragmentScope
internal interface FiltersBottomSheetFragmentComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance fragment: BottomSheetDialogFragment,
            @BindsInstance viewModel: MovieCatalogViewModel,
        ): FiltersBottomSheetFragmentComponent
    }

    val fragment: BottomSheetDialogFragment
    val viewModel: MovieCatalogViewModel
    val filtersEpoxyController: FiltersEpoxyController
}

@Scope
annotation class FiltersBottomSheetFragmentScope