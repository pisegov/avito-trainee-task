package com.myaxa.movies_catalog.ui

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.myaxa.movies.common.setOnTextChangeListener
import com.myaxa.movies_catalog.MoviesCatalogViewModel
import com.myaxa.movies_catalog.databinding.FragmentMoviesCatalogBinding
import kotlinx.coroutines.flow.collectLatest
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

    fun setupObservers(binding: FragmentMoviesCatalogBinding) {
        lifecycleScope.launch {
            viewModel.catalogFlow.collectLatest {
                catalogEpoxyController.submitData(it)
            }
        }

        lifecycleScope.launch {
            catalogEpoxyController.loadStateFlow.collect {
                val isDataEmpty = catalogEpoxyController.adapter.itemCount == 0
                    && (it.refresh != LoadState.Loading)
                binding.noDataText.isVisible = isDataEmpty

                binding.recycler.isVisible = it.refresh != LoadState.Loading
                binding.progress.isVisible = it.refresh == LoadState.Loading
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