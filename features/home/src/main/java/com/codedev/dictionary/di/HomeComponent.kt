package com.codedev.dictionary.di

import com.codedev.base._di.BaseFeatureComponent
import com.codedev.dictionary.HomeActivity
import com.codedev.dictionary.ui.word_result.di.WordResultComponent
import dagger.Component

@Component(
    modules = [HomeModule::class],
    dependencies = [BaseFeatureComponent::class]
)
@HomeScope
interface HomeComponent {

    companion object{
        @JvmStatic
        fun getInitialBuilder(): DaggerHomeComponent.Builder =
            DaggerHomeComponent.builder()
    }

    fun inject(activity: HomeActivity)

    fun wordResultComponent(): WordResultComponent.Builder

}