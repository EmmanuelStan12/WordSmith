package com.codedev.dictionary.ui.home._di

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codedev.base._di.ViewModelKey
import com.codedev.dictionary.di.MViewModelFactory
import com.codedev.dictionary.ui.home.HomeViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider

@Module
object HomeFeatureModule {

    @Provides
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    @HomeFragmentScope
    fun provideHomeViewModel(
        viewModelFactory: MViewModelFactory,
        activity: AppCompatActivity
    ): HomeViewModel =
        ViewModelProvider(activity.viewModelStore,viewModelFactory)[HomeViewModel::class.java]

}