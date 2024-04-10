package com.myaxa.avito_kinopoisk_test.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myaxa.movie.details.MovieDetailsViewModel
import com.myaxa.movies_catalog.MoviesCatalogViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

internal class ViewModelFactory @Inject constructor(
    private val viewModelFactories: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T  {
        return viewModelFactories.getValue(modelClass as Class<ViewModel>).get() as T
    }

    val viewModelClasses get() = viewModelFactories.keys
}

@Module
internal interface ViewModelModule {
    @Binds
    @ApplicationScope
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MoviesCatalogViewModel::class)
    fun moviesCatalogViewModel(viewModel: MoviesCatalogViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    fun movieDetailsViewModel(viewModel: MovieDetailsViewModel): ViewModel
}

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)
