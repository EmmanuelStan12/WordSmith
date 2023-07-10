package com.codedev.base._di

import android.content.Context
import com.codedev.data_lib.repositories.interfaces.IVideoRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [BaseFeatureModule::class]
)
@Singleton
interface BaseFeatureComponent {

    fun getVideoRepository(): IVideoRepository

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder
        fun build(): BaseFeatureComponent
    }

}