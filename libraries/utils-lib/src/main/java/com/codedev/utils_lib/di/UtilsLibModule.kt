package com.codedev.utils_lib.di

import android.content.Context
import com.codedev.utils_lib.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by Joshua Sylvanus, 7:31 AM, 03-Jan-2023.
 */
@Module
object UtilsLibModule {
    @Provides
    @Singleton
    fun providePreferenceManager(context:Context): PreferenceManager =
        PreferenceManager(context)
}