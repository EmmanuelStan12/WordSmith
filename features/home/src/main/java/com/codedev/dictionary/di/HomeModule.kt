package com.codedev.dictionary.di

import androidx.lifecycle.ViewModel
import com.codedev.base._di.ViewModelKey
import com.codedev.dictionary.ui.home.HomeViewModel
import com.codedev.dictionary.ui.home._di.HomeFragmentScope
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider

@Module
object HomeModule {

    @Provides
    @HomeScope
    fun provideViewModelFactory(
        map: Map<Class<out ViewModel>,
        @JvmSuppressWildcards Provider<ViewModel>>
    ): MViewModelFactory = MViewModelFactory(map)

}