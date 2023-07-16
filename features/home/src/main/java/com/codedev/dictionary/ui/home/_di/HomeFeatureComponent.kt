package com.codedev.dictionary.ui.home._di

import androidx.fragment.app.Fragment
import com.codedev.base._di.BaseFeatureComponent
import com.codedev.dictionary.ui.home.HomeFragment
import com.codedev.dictionary.ui.home.HomeViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent

@Subcomponent(
    modules = [HomeFeatureModule::class]
)
@HomeFragmentScope
interface HomeFeatureComponent {

    fun inject(fragment: HomeFragment)

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun fragment(fragment: Fragment): Builder
        fun build(): HomeFeatureComponent
    }

}