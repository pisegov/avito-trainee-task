package com.myaxa.movies_catalog.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myaxa.movies_catalog.MoviesCatalogViewModel
import com.myaxa.movies_catalog.R
import com.myaxa.movies_catalog.ScreenState
import com.myaxa.movies_catalog.databinding.FragmentMoviesCatalogBinding
import com.myaxa.movies_catalog.di.DaggerMoviesCatalogComponent
import com.myaxa.movies_catalog.di.MoviesCatalogComponent
import com.myaxa.movies_catalog.di.MoviesCatalogDependencies
import com.myaxa.movies_catalog.di.MoviesCatalogDependenciesProvider
import kotlinx.coroutines.launch

class MoviesCatalogFragment : Fragment(R.layout.fragment_movies_catalog) {

    private val binding by viewBinding(FragmentMoviesCatalogBinding::bind)

    private lateinit var component: MoviesCatalogComponent

    private val dependencies: MoviesCatalogDependencies
        get() = (requireActivity().applicationContext as MoviesCatalogDependenciesProvider).provideDependencies()

    private val viewModel: MoviesCatalogViewModel by viewModels { dependencies.viewModelFactory }

    private lateinit var catalogController: MoviesEpoxyController

    override fun onAttach(context: Context) {
        super.onAttach(context)

        component = DaggerMoviesCatalogComponent.factory().create(
            dependencies = dependencies,
            viewModel = viewModel,
        )

        catalogController = component.moviesEpoxyController
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()

        setupObservers()
    }

    private fun setupViews() {
        binding.recycler.apply {
            val layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

            setController(catalogController)
            setLayoutManager(layoutManager)
        }
    }

    private fun setupObservers() {

        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is ScreenState.Success -> {
                        catalogController.movies = it.movies
                    }

                    ScreenState.Loading -> {
                        Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
                    }

                    ScreenState.NetworkError -> {
                        Toast.makeText(requireContext(), "NetworkError", Toast.LENGTH_SHORT).show()
                    }

                    ScreenState.NoDataError -> {
                        Toast.makeText(requireContext(), "There is no data in database", Toast.LENGTH_SHORT).show()
                    }

                    ScreenState.None -> {}
                }
            }
        }

        lifecycleScope.launch {
            viewModel.navigateToDetails.collect {
                it?.let {
                    Toast.makeText(requireContext(), "Navigate to movie $it", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}