package com.codedev.dictionary.ui.word_result.di

import androidx.fragment.app.Fragment
import com.codedev.dictionary.ui.word_result.WordResultFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [WordResultModule::class]
)
@WordResultFragmentScope
interface WordResultComponent {

    fun inject(fragment: WordResultFragment)

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun fragment(fragment: Fragment): Builder
        fun build(): WordResultComponent
    }
}