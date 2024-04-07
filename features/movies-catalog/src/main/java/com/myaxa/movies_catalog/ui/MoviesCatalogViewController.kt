package com.myaxa.movies_catalog.ui

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.GridLayoutManager
import com.myaxa.movies.common.setOnTextChangeListener
import com.myaxa.movies_catalog.MoviesCatalogViewModel
import com.myaxa.movies_catalog.ScreenState
import com.myaxa.movies_catalog.databinding.FragmentMoviesCatalogBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesCatalogViewController @Inject constructor(
    private val fragment: Fragment,
    private val catalogEpoxyController: MoviesEpoxyController,
    private val viewModel: MoviesCatalogViewModel,
    private val lifecycleScope: LifecycleCoroutineScope,
) {
    fun setupViews(binding: FragmentMoviesCatalogBinding) {
        with(binding) {
            recycler.apply {
                val layoutManager = GridLayoutManager(
                    fragment.requireContext(),
                    2,
                    GridLayoutManager.VERTICAL,
                    false
                )

                setController(catalogEpoxyController)
                setLayoutManager(layoutManager)
            }
            searchView.editText.setOnTextChangeListener {
                val text = it.toString()
                viewModel.updateSearchQuery(text)
            }

            searchView.editText.setOnEditorActionListener { v, actionId, event ->
                searchBar.setText(searchView.text)
                searchView.hide()
                false
            }
        }
    }

    fun setupObservers() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is ScreenState.Success -> {
                        catalogEpoxyController.movies = it.movies
                    }

                    ScreenState.Loading -> {
                        Toast.makeText(fragment.requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
                    }

                    ScreenState.NetworkError -> {
                        Toast.makeText(fragment.requireContext(), "NetworkError", Toast.LENGTH_SHORT).show()
                    }

                    ScreenState.NoDataError -> {
                        Toast.makeText(fragment.requireContext(), "There is no data in database", Toast.LENGTH_SHORT).show()
                    }

                    ScreenState.None -> {}
                }
            }
        }

        lifecycleScope.launch {
            viewModel.navigateToDetails.collect {
                it?.let {
                    Toast.makeText(fragment.requireContext(), "Navigate to movie $it", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}