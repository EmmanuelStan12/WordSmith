package com.codedev.dictionary.di

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codedev.base._di.ViewModelKey
import com.codedev.dictionary.ui.common.MainViewModel
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

    @Provides
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @HomeScope
    fun provideMainViewModel(
        viewModelFactory: MViewModelFactory,
        activity: AppCompatActivity
    ): MainViewModel =
        ViewModelProvider(activity.viewModelStore,viewModelFactory)[MainViewModel::class.java]

}