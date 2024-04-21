package com.myaxa.filters_bottomsheet_impl.di

import com.myaxa.filters_bottomsheet_api.FiltersBottomSheetApi
import dagger.Binds
import dagger.Module

@Module
abstract class FiltersBottomSheetApiModule {

    @Binds
    internal abstract fun bindFiltersBottomSheetApi(impl: FiltersBottomSheetApiImpl): FiltersBottomSheetApi
}
