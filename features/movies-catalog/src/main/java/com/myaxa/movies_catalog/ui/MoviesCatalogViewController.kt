package com.myaxa.movies_catalog.ui

import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyRecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.myaxa.movie.details.api.MovieDetailsApi
import com.myaxa.movies.common.Navigator
import com.myaxa.movies.common.setOnTextChangeListener
import com.myaxa.movies_catalog.MoviesCatalogViewModel
import com.myaxa.movies_catalog.databinding.FragmentMoviesCatalogBinding
import com.myaxa.movies_catalog.ui.filters.bottomsheet.FiltersBottomSheetFragment
import com.myaxa.movies_catalog.ui.filters.selected_filters.SelectedFiltersEpoxyController
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesCatalogViewController @Inject constructor(
    private val fragment: Fragment,
    catalogEpoxyControllerFactory: MoviesEpoxyController.Factory,
    private val filtersEpoxyController: SelectedFiltersEpoxyController,
    private val viewModel: MoviesCatalogViewModel,
    private val lifecycleScope: LifecycleCoroutineScope,
    private val movieDetailsApi: MovieDetailsApi,
    private val navigator: Navigator,
) {
    private var isRefreshing = false

    private val catalogEpoxyController: MoviesEpoxyController = catalogEpoxyControllerFactory.create {
        navigator.navigateToFragment(fragment.parentFragmentManager, movieDetailsApi.provideMovieDetails(it))
    }

    fun setupViews(binding: FragmentMoviesCatalogBinding) = with(binding) {

        setupCatalog(catalog, noDataText)

        setupFilters(filters, btnFilters)

        setupSearchBar(searchBar)

        setupObservers(binding)
    }

    private fun setupCatalog(catalog: EpoxyRecyclerView, noDataTextView: TextView) {
        catalog.apply {

            catalogEpoxyController.adapter.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            catalogEpoxyController.addInterceptor(
                CheckIfListIsEmptyInterceptor { noDataTextView.isVisible = it && !isRefreshing }
            )
            setController(catalogEpoxyController)

            val layoutManager = GridLayoutManager(
                fragment.requireContext(),
                2,
                GridLayoutManager.VERTICAL,
                false
            )
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

    private fun setupSearchBar(searchBar: EditText) {
        searchBar.setOnTextChangeListener {
            val text = it.toString()
            viewModel.updateSearchQuery(text)
        }
    }

    private fun setupObservers(binding: FragmentMoviesCatalogBinding) {
        lifecycleScope.launch {
            viewModel.catalogFlow.collect {
                catalogEpoxyController.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModel.filtersFlow.collect {
                filtersEpoxyController.filters = it
                binding.filters.isVisible = it?.isSelected == true
            }
        }

        lifecycleScope.launch {
            catalogEpoxyController.loadStateFlow.collect {
                isRefreshing = it.refresh == LoadState.Loading
                binding.progress.isVisible = isRefreshing
                if (isRefreshing) {
                    binding.noDataText.isVisible = false
                }
            }
        }
    }
}