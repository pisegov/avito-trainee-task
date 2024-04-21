package com.myaxa.filters_bottomsheet_impl.di

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myaxa.filters_bottomsheet_api.FiltersBottomSheetApi
import com.myaxa.filters_bottomsheet_impl.ui.FiltersBottomSheetFragment
import javax.inject.Inject

class FiltersBottomSheetApiImpl @Inject constructor() : FiltersBottomSheetApi {
    override fun provideFiltersBottomSheet(): BottomSheetDialogFragment {
        return FiltersBottomSheetFragment()
    }
}
