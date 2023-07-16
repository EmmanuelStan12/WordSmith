package com.codedev.dictionary.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider

class MViewModelFactory(
        private val viewModels: Map<Class<out ViewModel>,
        @JvmSuppressWildcards Provider<ViewModel>>,
    ) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModels[modelClass]?.get() as? T ?:
            throw NullPointerException("ViewModel ${modelClass.name} not assignable")
    }
}