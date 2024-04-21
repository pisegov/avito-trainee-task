package com.myaxa.filters_bottomsheet_impl.ui

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myaxa.filters_bottomsheet_impl.R
import com.myaxa.filters_bottomsheet_impl.databinding.BottomsheetFiltersBinding
import com.myaxa.filters_bottomsheet_impl.databinding.BottomsheetStickyButtonsBinding
import com.myaxa.filters_bottomsheet_impl.di.DaggerFiltersBottomSheetComponent
import com.myaxa.filters_bottomsheet_impl.di.FiltersBottomSheetComponent
import com.myaxa.filters_bottomsheet_impl.ui.epoxy_controllers.FiltersEpoxyController
import com.myaxa.filters_bottomsheet_impl.util.addKeyboardListener
import com.myaxa.movie_catalog_api.MovieCatalogDependenciesProvider
import com.myaxa.movie_catalog_api.MovieCatalogViewModel
import com.myaxa.movies.common.dpToPx
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class FiltersBottomSheetFragment : BottomSheetDialogFragment() {
    private val binding by viewBinding(BottomsheetFiltersBinding::bind)

    private var buttonsBinding: BottomsheetStickyButtonsBinding? = null

    private val viewModelFactory
        get() = (requireActivity().application as MovieCatalogDependenciesProvider)
            .provideMovieCatalogDependencies()
            .viewModelFactory

    private val viewModel by viewModels<MovieCatalogViewModel>(
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
        buttonsBinding = BottomsheetStickyButtonsBinding.inflate(LayoutInflater.from(requireContext()))

        binding.filters.setController(filtersEpoxyController)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            binding.root.addKeyboardListener {
                buttonsBinding?.root?.isVisible = !it
            }
        }

        lifecycleScope.launch {
            viewModel.filtersFlow.collectLatest {
                it?.let {
                    filtersEpoxyController.updateFilters(it)
                }
            }
        }

        lifecycleScope.launch {
            filtersEpoxyController.filtersFlow.collect {
                buttonsBinding?.cancelButton?.isVisible = it?.isSelected == true
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState)

        bottomSheetDialog.setOnShowListener {
            buttonsBinding?.run {
                val containerLayout = dialog?.findViewById<FrameLayout>(
                    com.google.android.material.R.id.container
                )

                val layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM
                )

                containerLayout?.addView(
                    root,
                    layoutParams
                )

                root.viewTreeObserver?.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        root.viewTreeObserver?.removeOnGlobalLayoutListener(this)

                        val height = root.measuredHeight
                        binding.root.setPadding(0, 0, 0, height + 8.dpToPx())
                    }
                })

                okButton.setOnClickListener {
                    viewModel.updateFilters(filtersEpoxyController.filtersFlow.value)
                    dismiss()
                }

                cancelButton.isSelected = false
                cancelButton.setOnClickListener {
                    filtersEpoxyController.clearFilters()
                    viewModel.updateFilters(filtersEpoxyController.filtersFlow.value)
                }
            }
        }

        return bottomSheetDialog
    }

    override fun onDestroy() {
        buttonsBinding = null
        super.onDestroy()
    }
}