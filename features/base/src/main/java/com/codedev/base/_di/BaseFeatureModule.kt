package com.codedev.base._di

import com.codedev.data_lib._di.DataModule
import com.codedev.room_lib._di.RoomModule
import com.codedev.utils_lib.di.UtilsLibModule
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module(includes = [
    RoomModule::class,
    DataModule::class,
    UtilsLibModule::class
])
object BaseFeatureModule {
    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

}