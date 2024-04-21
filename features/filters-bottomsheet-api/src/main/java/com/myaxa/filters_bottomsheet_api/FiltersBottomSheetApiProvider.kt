package com.myaxa.filters_bottomsheet_api

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

interface FiltersBottomSheetApiProvider {
    fun provideFiltersBottomSheetApi(): FiltersBottomSheetApi
}

interface FiltersBottomSheetApi {
    fun provideFiltersBottomSheet(): BottomSheetDialogFragment
}
