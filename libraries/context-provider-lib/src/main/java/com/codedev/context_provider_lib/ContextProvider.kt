package com.freexitnow.context_provider_lib

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentResolver
import android.content.Context


/**
 * Created by Joshua Sylvanus, 2:12 PM, 01-Jan-2023.
 */
@SuppressLint("StaticFieldLeak")
object ContextProvider {
    private lateinit var application: Application
    private lateinit var contentResolver: ContentResolver

    fun setApplication(app: Application){
        application = app
    }
    fun getApplication(): Application = application

    fun getContentResolver(): ContentResolver = contentResolver
    fun setContentResolver(cr: ContentResolver){ contentResolver = cr }
}