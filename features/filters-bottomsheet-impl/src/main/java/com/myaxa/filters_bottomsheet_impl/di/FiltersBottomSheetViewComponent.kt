package com.myaxa.filters_bottomsheet_impl.di

import androidx.lifecycle.LifecycleOwner
import com.myaxa.filters_bottomsheet_impl.databinding.BottomsheetFiltersBinding
import com.myaxa.filters_bottomsheet_impl.databinding.BottomsheetStickyButtonsBinding
import com.myaxa.filters_bottomsheet_impl.ui.FiltersBottomSheetViewController
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Scope

@Component(
    dependencies = [FiltersBottomSheetFragmentComponent::class],
    modules = [FiltersBottomSheetViewModule::class]
)
@FiltersBottomSheetViewScope
internal interface FiltersBottomSheetViewComponent {

    @Component.Factory
    interface Factory {
        fun create(
            fragmentComponent: FiltersBottomSheetFragmentComponent,
            @BindsInstance binding: BottomsheetFiltersBinding,
            @BindsInstance buttonsBinding: BottomsheetStickyButtonsBinding,
            @BindsInstance lifecycleOwner: LifecycleOwner,
        ): FiltersBottomSheetViewComponent
    }

    val viewController: FiltersBottomSheetViewController
}

@Module
internal interface FiltersBottomSheetViewModule

@Scope
internal annotation class FiltersBottomSheetViewScope