package com.myaxa.movie_catalog_impl.ui

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
import com.myaxa.filters_bottomsheet_api.FiltersBottomSheetApi
import com.myaxa.movie_catalog_api.MovieCatalogViewModel
import com.myaxa.movie_catalog_impl.databinding.FragmentMovieCatalogBinding
import com.myaxa.movie_catalog_impl.ui.filters.SelectedFiltersEpoxyController
import com.myaxa.movies.common.setOnTextChangeListener
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class MovieCatalogViewController @Inject constructor(
    private val fragment: Fragment,
    private val binding: FragmentMovieCatalogBinding,
    private val catalogEpoxyController: MovieCatalogEpoxyController,
    private val filtersEpoxyController: SelectedFiltersEpoxyController,
    private val viewModel: MovieCatalogViewModel,
    private val lifecycleScope: LifecycleCoroutineScope,
    private val filtersBottomSheetApi: FiltersBottomSheetApi,
) {
    private var isRefreshing = false

    fun setupViews() = with(binding) {

        setupCatalog(catalog, noDataText)

        setupFilters(filters, btnFilters)

        setupSearchBar(searchBar)

        setupObservers()
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

    private fun setupFilters(filters: EpoxyRecyclerView, btnFilters: FloatingActionButton) {
        filters.setController(filtersEpoxyController)

        btnFilters.setOnClickListener {
            filtersBottomSheetApi.provideFiltersBottomSheet()
                .show(fragment.childFragmentManager, "filters_bottomsheet")
        }
    }

    private fun setupSearchBar(searchBar: EditText) {
        searchBar.setOnTextChangeListener {
            val text = it.toString()
            viewModel.updateSearchQuery(text)
        }
    }

    private fun setupObservers() {
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