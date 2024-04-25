package com.myaxa.filters_bottomsheet_impl.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myaxa.filters_bottomsheet_impl.databinding.BottomsheetFiltersBinding
import com.myaxa.filters_bottomsheet_impl.databinding.BottomsheetStickyButtonsBinding
import com.myaxa.filters_bottomsheet_impl.di.DaggerFiltersBottomSheetFragmentComponent
import com.myaxa.filters_bottomsheet_impl.di.DaggerFiltersBottomSheetViewComponent
import com.myaxa.filters_bottomsheet_impl.di.FiltersBottomSheetFragmentComponent
import com.myaxa.filters_bottomsheet_impl.di.FiltersBottomSheetViewComponent
import com.myaxa.movie_catalog_api.MovieCatalogDependenciesProvider
import com.myaxa.movie_catalog_api.MovieCatalogViewModel
import com.myaxa.movies.common.unsafeLazy

internal class FiltersBottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModelFactory
        get() = (requireActivity().application as MovieCatalogDependenciesProvider)
            .provideMovieCatalogDependencies()
            .viewModelFactory

    private val viewModel by viewModels<MovieCatalogViewModel>(
        ownerProducer = { requireParentFragment() },
        factoryProducer = { viewModelFactory },
    )

    private val component: FiltersBottomSheetFragmentComponent by unsafeLazy {
        DaggerFiltersBottomSheetFragmentComponent.factory().create(this, viewModel)
    }

    private var viewComponent: FiltersBottomSheetViewComponent? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        val binding = BottomsheetFiltersBinding.inflate(inflater)
        val buttonsBinding = BottomsheetStickyButtonsBinding.inflate(inflater)

        viewComponent = DaggerFiltersBottomSheetViewComponent.factory().create(
            fragmentComponent = component,
            binding = binding,
            buttonsBinding = buttonsBinding,
            lifecycleOwner = viewLifecycleOwner,
        ).apply {
            viewController.setUpViews()
        }

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

       return super.onCreateDialog(savedInstanceState).apply {
           setOnShowListener {
               viewComponent?.viewController?.setUpStickyButtons()
           }
       }
    }

    override fun onDestroyView() {

        super.onDestroyView()
        viewComponent = null
    }
}