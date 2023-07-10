package com.codedev.dictionary

import android.app.Application
import com.codedev.base._di.BaseFeatureComponent
import com.codedev.base._di.BaseFeatureComponentProvider
import com.codedev.base._di.DaggerBaseFeatureComponent
import com.freexitnow.context_provider_lib.ContextProvider
import timber.log.Timber


class BaseApplication: Application(), BaseFeatureComponentProvider {

    private lateinit var baseFeatureComponent: BaseFeatureComponent

    override fun onCreate() {
        super.onCreate()
        initDI()
        initTimber()
    }

    private fun initializeComponent() {
        baseFeatureComponent = DaggerBaseFeatureComponent.builder()
            .applicationContext(applicationContext)
            .build()
    }

    private fun initDI(){
        ContextProvider.setApplication(this)
        ContextProvider.setContentResolver(contentResolver)
        initializeComponent()
    }

    private fun initTimber(){
        if(!BuildConfig.DEBUG)
            return

        Timber.plant(object: Timber.DebugTree(){
            override fun createStackElementTag(element: StackTraceElement): String {
                return String.format(
                    "%s:%s",
                    element.methodName,
                    super.createStackElementTag(element))
            }
        })
    }

    override fun provideBaseComponent(): BaseFeatureComponent {
        return baseFeatureComponent
    }
}