package com.myaxa.movies_catalog.ui

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.epoxy.EpoxyRecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import com.myaxa.movies.common.setOnTextChangeListener
import com.myaxa.movies_catalog.MoviesCatalogViewModel
import com.myaxa.movies_catalog.databinding.FragmentMoviesCatalogBinding
import com.myaxa.movies_catalog.ui.filters.ChosenFiltersEpoxyController
import com.myaxa.movies_catalog.ui.filters.FiltersBottomSheetFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesCatalogViewController @Inject constructor(
    private val fragment: Fragment,
    private val catalogEpoxyController: MoviesEpoxyController,
    private val filtersEpoxyController: ChosenFiltersEpoxyController,
    private val viewModel: MoviesCatalogViewModel,
    private val lifecycleScope: LifecycleCoroutineScope,
) {
    fun setupViews(binding: FragmentMoviesCatalogBinding) = with(binding) {

        setupCatalog(catalog)

        setupFilterCardsList(filters, btnFilters)

        setupSearch(searchView, searchBar)
    }

    fun setupObservers(binding: FragmentMoviesCatalogBinding) {
        lifecycleScope.launch {
            viewModel.catalogFlow.collectLatest {
                catalogEpoxyController.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModel.filtersFlow.collect {
                filtersEpoxyController.filters = it
            }
        }

        lifecycleScope.launch {
            catalogEpoxyController.loadStateFlow.collect {
                val isDataEmpty = catalogEpoxyController.adapter.itemCount == 0
                    && (it.refresh != LoadState.Loading)

                binding.noDataText.isVisible = isDataEmpty
                binding.catalog.isVisible = it.refresh != LoadState.Loading
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

    private fun setupCatalog(catalog: EpoxyRecyclerView) {
        catalog.apply {
            val layoutManager = GridLayoutManager(
                fragment.requireContext(),
                2,
                GridLayoutManager.VERTICAL,
                false
            )

            setController(catalogEpoxyController)
            setLayoutManager(layoutManager)
        }
    }

    private fun setupFilterCardsList(filters: EpoxyRecyclerView, btnFilters: FloatingActionButton) {
        filters.setController(filtersEpoxyController)

        btnFilters.setOnClickListener {
            FiltersBottomSheetFragment()
                .show(fragment.childFragmentManager, "filters_bottomsheet")
        }
    }

    private fun setupSearch(searchView: SearchView, searchBar: SearchBar) {
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