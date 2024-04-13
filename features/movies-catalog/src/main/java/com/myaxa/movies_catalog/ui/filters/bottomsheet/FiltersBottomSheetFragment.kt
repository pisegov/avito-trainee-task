package com.myaxa.movies_catalog.ui.filters.bottomsheet

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myaxa.movies_catalog.MoviesCatalogViewModel
import com.myaxa.movies_catalog.R
import com.myaxa.movies_catalog.databinding.BottomsheetFiltersBinding
import com.myaxa.movies_catalog.di.MoviesCatalogDependenciesProvider
import com.myaxa.movies_catalog.ui.filters.bottomsheet.epoxy_controllers.FiltersEpoxyController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FiltersBottomSheetFragment : BottomSheetDialogFragment() {
    private val binding by viewBinding(BottomsheetFiltersBinding::bind)

    private val viewModelFactory
        get() = (requireActivity().application as MoviesCatalogDependenciesProvider)
            .provideMovieCatalogDependencies()
            .viewModelFactory

    private val viewModel by viewModels<MoviesCatalogViewModel>(
        ownerProducer = { requireParentFragment() },
        factoryProducer = { viewModelFactory },
    )

    private lateinit var component: FiltersBottomSheetComponent

    private lateinit var filtersEpoxyController: FiltersEpoxyController

    override fun onAttach(context: Context) {
        super.onAttach(context)

        component = DaggerFiltersBottomSheetComponent.factory().create(viewModel)
        filtersEpoxyController = component.filtersEpoxyController
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.bottomsheet_filters, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        BottomSheetBehavior.from(binding.bottomsheet).apply {
            state = STATE_EXPANDED
        }

        binding.bottomsheet.minimumHeight = Resources.getSystem().displayMetrics.heightPixels
        binding.filters.setController(filtersEpoxyController)
        binding.filters.isNestedScrollingEnabled = false

        lifecycleScope.launch {
            viewModel.filtersFlow.collectLatest {
                it?.let {
                    filtersEpoxyController.updateFilters(it)
                }
            }
        }

        lifecycleScope.launch {
            filtersEpoxyController.filtersFlow.collect {
                viewModel.updateFilters(filtersEpoxyController.filtersFlow.value)
            }
        }
    }
}