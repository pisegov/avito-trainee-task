package com.myaxa.movies_catalog.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myaxa.movies_catalog.MoviesCatalogViewModel
import com.myaxa.movies_catalog.R
import com.myaxa.movies_catalog.databinding.FragmentMoviesCatalogBinding
import com.myaxa.movies_catalog.di.DaggerMoviesCatalogComponent
import com.myaxa.movies_catalog.di.DaggerMoviesCatalogViewComponent
import com.myaxa.movies_catalog.di.MoviesCatalogComponent
import com.myaxa.movies_catalog.di.MoviesCatalogDependencies
import com.myaxa.movies_catalog.di.MoviesCatalogDependenciesProvider
import com.myaxa.movies_catalog.di.MoviesCatalogViewComponent

class MoviesCatalogFragment : Fragment(R.layout.fragment_movies_catalog) {

    private val binding by viewBinding(FragmentMoviesCatalogBinding::bind)

    private lateinit var component: MoviesCatalogComponent
    private lateinit var viewComponent: MoviesCatalogViewComponent
    private lateinit var viewController: MoviesCatalogViewController

    private val dependencies: MoviesCatalogDependencies
        get() = (requireActivity().applicationContext as MoviesCatalogDependenciesProvider).provideDependencies()

    private val viewModel: MoviesCatalogViewModel by viewModels { dependencies.viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        component = DaggerMoviesCatalogComponent.factory().create(
            dependencies = dependencies,
            fragment = this,
            viewModel = viewModel,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewComponent = DaggerMoviesCatalogViewComponent.factory().create(
            moviesCatalogComponent = component,
            lifecycleScope = lifecycleScope,
        )

        viewController = viewComponent.viewController

        viewController.setupViews(binding)

        viewController.setupObservers()
    }
}