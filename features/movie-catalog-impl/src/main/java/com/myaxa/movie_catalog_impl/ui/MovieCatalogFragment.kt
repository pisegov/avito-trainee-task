package com.myaxa.movie_catalog_impl.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
import com.myaxa.movies.common.unsafeLazy

internal class MovieCatalogFragment : Fragment(R.layout.fragment_movie_catalog) {

    private val dependencies: MovieCatalogDependencies
        get() = (requireActivity().applicationContext as MovieCatalogDependenciesProvider)
            .provideMovieCatalogDependencies()

    private val viewModel: MovieCatalogViewModel by viewModels { dependencies.viewModelFactory }

    private val component: MovieCatalogComponent by unsafeLazy {
        DaggerMovieCatalogComponent.factory().create(
            fragment = this,
            viewModel = viewModel,
            navigator = dependencies.navigator,
            movieDetailsApi = (requireActivity().applicationContext as MovieDetailsApiProvider).provideMovieDetailsApi(),
            filtersBottomSheetApi = (requireActivity().applicationContext as FiltersBottomSheetApiProvider).provideFiltersBottomSheetApi(),
        )
    }

    private var viewComponent: MovieCatalogViewComponent? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val binding = FragmentMovieCatalogBinding.inflate(inflater)
        viewComponent = DaggerMovieCatalogViewComponent.factory().create(
            moviesCatalogComponent = component,
            binding = binding,
            lifecycleScope = viewLifecycleOwner.lifecycleScope,
        ).apply {
            viewController.setupViews()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewComponent = null
    }
}