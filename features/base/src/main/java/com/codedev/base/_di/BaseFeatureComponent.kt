package com.codedev.base._di

import android.content.Context
import com.codedev.data_lib.repositories.interfaces.HistoryRepository
import com.codedev.data_lib.repositories.interfaces.MainRepository
import com.codedev.room_lib.DictionaryDatabase
import com.codedev.utils_lib.PreferenceManager
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [BaseFeatureModule::class]
)
@Singleton
interface BaseFeatureComponent {

    fun getDictionaryDatabase(): DictionaryDatabase
    fun getPreferencesStore(): PreferenceManager
    fun getMainRepository(): MainRepository
    fun getHistoryRepository(): HistoryRepository

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder
        fun build(): BaseFeatureComponent
    }

}