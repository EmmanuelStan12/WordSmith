package com.codedev.dictionary.ui.word_result.di

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.codedev.base._di.ViewModelKey
import com.codedev.dictionary.di.MViewModelFactory
import com.codedev.dictionary.ui.word_result.WordResultViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
object WordResultModule {

    @Provides
    @IntoMap
    @ViewModelKey(WordResultViewModel::class)
    @WordResultFragmentScope
    fun provideWordResultViewModel(
        viewModelFactory: MViewModelFactory,
        activity: AppCompatActivity
    ): WordResultViewModel {
        return ViewModelProvider(activity.viewModelStore,viewModelFactory)[WordResultViewModel::class.java]
    }
}