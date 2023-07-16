package com.codedev.utils_lib

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first


/**
 * Created by Joshua Sylvanus, 8:01 PM, 18/05/2022.
 */
const val KEY_PREFS_DEFAULT = "com.codedev.wordsmith.KEY_PREFS_DEFAULT"


class PreferenceManager(val context: Context) {

    internal val prefs:SharedPreferences by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        context.getSharedPreferences(KEY_PREFS_DEFAULT, Context.MODE_PRIVATE)
    }

    private val editor:SharedPreferences.Editor = prefs.edit()

    val Context.dataStore: DataStore<Preferences> by androidx.datastore.preferences.preferencesDataStore(name = KEY_PREFS_DEFAULT)

    val coroutineScope:CoroutineScope = CoroutineScope(Job() + Dispatchers.IO)

    fun clearPreference(vararg keys:String):Unit =
        keys.toList().forEach { editor.remove(it).apply() }

    fun set(key:String, value:Any){
        CoroutineScope(Dispatchers.Main).launch{
            when(value){
                is Boolean -> editor.putBoolean(key, value).apply()
                is String -> editor.putString(key, value).apply()
                is Int -> editor.putInt(key, value).apply()
                is Float -> editor.putFloat(key, value).apply()
                is Long -> editor.putLong(key, value).apply()
                else -> throw IllegalArgumentException("type is not supported")
            }
        }
    }

    inline fun <reified T> setDS(key:String, value:T){
        val preferenceKey:Preferences.Key<*> =
        when(T::class){
            Boolean::class -> booleanPreferencesKey(key)
            String::class -> stringPreferencesKey(key)
            Int::class -> intPreferencesKey(key)
            Float::class -> floatPreferencesKey(key)
            Double::class -> doublePreferencesKey(key)
            Long::class -> longPreferencesKey(key)
            else -> throw IllegalArgumentException()
        }

        coroutineScope.launch {
            context.dataStore.edit {
                it.set(preferenceKey as Preferences.Key<T>, value)
            }
        }
    }

    suspend inline fun <reified T> getDS(key:String, defaultValue:T? = null):T?{
        val preferenceKey:Preferences.Key<*> =
            when(T::class){
                Boolean::class -> booleanPreferencesKey(key)
                String::class -> stringPreferencesKey(key)
                Int::class -> intPreferencesKey(key)
                Float::class -> floatPreferencesKey(key)
                Double::class -> doublePreferencesKey(key)
                Long::class -> longPreferencesKey(key)
                else -> throw IllegalArgumentException()
            }

        return withContext(Dispatchers.IO){
            context.dataStore.data.first()[preferenceKey as Preferences.Key<T>] as T ?: defaultValue
        }
    }

}