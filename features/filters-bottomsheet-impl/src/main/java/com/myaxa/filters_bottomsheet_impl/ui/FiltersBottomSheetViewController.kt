package com.myaxa.filters_bottomsheet_impl.ui

import android.os.Build
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import com.airbnb.epoxy.EpoxyRecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myaxa.filters_bottomsheet_impl.databinding.BottomsheetFiltersBinding
import com.myaxa.filters_bottomsheet_impl.databinding.BottomsheetStickyButtonsBinding
import com.myaxa.filters_bottomsheet_impl.di.FiltersBottomSheetViewScope
import com.myaxa.filters_bottomsheet_impl.ui.epoxy_controllers.FiltersEpoxyController
import com.myaxa.filters_bottomsheet_impl.util.addKeyboardListener
import com.myaxa.movie_catalog_api.MovieCatalogViewModel
import com.myaxa.movies.common.collectOnLifecycle
import com.myaxa.movies.common.dpToPx
import javax.inject.Inject

@FiltersBottomSheetViewScope
internal class FiltersBottomSheetViewController @Inject constructor(
    private val fragment: BottomSheetDialogFragment,
    private val mainBinding: BottomsheetFiltersBinding,
    private val buttonsBinding: BottomsheetStickyButtonsBinding,
    private val viewModel: MovieCatalogViewModel,
    private val epoxyController: FiltersEpoxyController,
    private val lifecycleOwner: LifecycleOwner,
) {
    fun setUpViews() {

        setUpFilters(mainBinding.filters)

        setUpKeyboardListener(mainBinding.root)
    }

    fun setUpStickyButtons() = with(buttonsBinding) {

        addStickyButtonsContainerToView()

        setUpOkButton(okButton)

        setUpClearButton(clearButton)
    }

    private fun setUpFilters(filtersRecycler: EpoxyRecyclerView) {
        filtersRecycler.setController(epoxyController)
        viewModel.filtersFlow.collectOnLifecycle(lifecycleOwner) { filters ->
            filters?.let {
                epoxyController.updateFilters(it)
            }
        }
    }

    // Need to hide sticky buttons when keyboard is shown
    private fun setUpKeyboardListener(rootView: ViewGroup) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            rootView.addKeyboardListener { keyboardIsVisible ->
                buttonsBinding.root.isVisible = !keyboardIsVisible
            }
        }
    }

    private fun addStickyButtonsContainerToView() = with(buttonsBinding) {
        val dialogContainerLayout = fragment.dialog?.findViewById<FrameLayout>(
            com.google.android.material.R.id.container
        )

        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT,
            Gravity.BOTTOM
        )

        dialogContainerLayout?.addView(
            root,
            layoutParams
        )

        root.viewTreeObserver?.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                root.viewTreeObserver?.removeOnGlobalLayoutListener(this)

                val height = root.measuredHeight
                mainBinding.root.setPadding(0, 0, 0, height + 8.dpToPx())
            }
        })
    }

    private fun setUpOkButton(okButton: Button) {

        okButton.setOnClickListener {
            viewModel.updateFilters(epoxyController.filtersFlow.value)
            fragment.dismiss()
        }
    }

    private fun setUpClearButton(clearButton: Button) {

        clearButton.isSelected = false
        clearButton.setOnClickListener {
            epoxyController.clearFilters()
            viewModel.updateFilters(epoxyController.filtersFlow.value)
        }
        epoxyController.filtersFlow.collectOnLifecycle(lifecycleOwner) {
            clearButton.isVisible = it?.isSelected == true
        }
    }
}
