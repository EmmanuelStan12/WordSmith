package com.codedev.room_lib.utils

import android.app.Person
import android.content.Context
import com.codedev.room_lib.models.DefinitionEntity
import com.codedev.room_lib.models.MeaningEntity
import com.codedev.room_lib.models.PhoneticEntity
import com.codedev.room_lib.models.WordEntity
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


object DictionaryFileLoader {

    suspend inline fun <reified T> loadJSONFromAsset(fileName: String, context: Context): List<T> {
        val items = mutableListOf<T>()
        try {
            val inputStream: InputStream = context.assets.open(fileName)
            val reader = JsonReader(InputStreamReader(inputStream));
            reader.beginArray()
            val gson = Gson()
            while (reader.hasNext()) {
                when(T::class) {
                    WordEntity::class -> {
                        val word = gson.fromJson<WordEntity>(reader, WordEntity::class.java)
                        items.add(word as T)
                    }
                    PhoneticEntity::class -> {
                        val phonetic = gson.fromJson<PhoneticEntity>(reader, PhoneticEntity::class.java)
                        items.add(phonetic as T)
                    }
                    MeaningEntity::class -> {
                        val meaning = gson.fromJson<MeaningEntity>(reader, MeaningEntity::class.java)
                        items.add(meaning as T)
                    }
                    DefinitionEntity::class -> {
                        val definition = gson.fromJson<DefinitionEntity>(reader, DefinitionEntity::class.java)
                        items.add(definition as T)
                    }

                }
            }
            reader.endArray()
            withContext(Dispatchers.IO) {
                inputStream.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            throw e
        }

        return items
    }
}