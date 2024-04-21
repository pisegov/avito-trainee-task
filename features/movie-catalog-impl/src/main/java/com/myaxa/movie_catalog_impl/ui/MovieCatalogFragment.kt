package com.myaxa.movie_catalog_impl.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myaxa.filters_bottomsheet_api.FiltersBottomSheetApiProvider
import com.myaxa.movie.details.api.MovieDetailsApiProvider
import com.myaxa.movie_catalog_api.MovieCatalogDependencies
import com.myaxa.movie_catalog_api.MovieCatalogDependenciesProvider
import com.myaxa.movie_catalog_api.MovieCatalogViewModel
import com.myaxa.movie_catalog_impl.R
import com.myaxa.movie_catalog_impl.databinding.FragmentMovieCatalogBinding
import com.myaxa.movie_catalog_impl.di.DaggerMovieCatalogComponent
import com.myaxa.movie_catalog_impl.di.DaggerMovieCatalogViewComponent
import com.myaxa.movie_catalog_impl.di.MovieCatalogComponent
import com.myaxa.movie_catalog_impl.di.MovieCatalogViewComponent

class MovieCatalogFragment : Fragment(R.layout.fragment_movie_catalog) {

    private val binding by viewBinding(FragmentMovieCatalogBinding::bind)

    private lateinit var component: MovieCatalogComponent
    private var viewComponent: MovieCatalogViewComponent? = null
    private var viewController: MovieCatalogViewController? = null

    private val dependencies: MovieCatalogDependencies
        get() = (requireActivity().applicationContext as MovieCatalogDependenciesProvider)
            .provideMovieCatalogDependencies()

    private val viewModel: MovieCatalogViewModel by viewModels { dependencies.viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        component = DaggerMovieCatalogComponent.factory().create(
            fragment = this,
            viewModel = viewModel,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewComponent = DaggerMovieCatalogViewComponent.factory().create(
            moviesCatalogComponent = component,
            lifecycleScope = lifecycleScope,
            navigator = dependencies.navigator,
            movieDetailsApi = (requireActivity().applicationContext as MovieDetailsApiProvider).provideMovieDetailsApi(),
            filtersBottomSheetApi = (requireActivity().applicationContext as FiltersBottomSheetApiProvider).provideFiltersBottomSheetApi(),
        )

        viewController = viewComponent?.viewController

        viewController?.setupViews(binding)
    }

    override fun onDestroyView() {
        viewComponent = null
        viewController = null

        super.onDestroyView()
    }
}