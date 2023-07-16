package com.codedev.room_lib.utils

import android.content.Context
import com.codedev.room_lib.models.DefinitionEntity
import com.codedev.room_lib.models.MeaningEntity
import com.codedev.room_lib.models.PhoneticEntity
import com.codedev.room_lib.models.WordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream

object DictionaryFileLoader {

    suspend inline fun <reified T> loadCSVFromAsset(fileName: String, context: Context): List<T> {
        val items = mutableListOf<T>()
        try {
            val inputStream: InputStream = context.assets.open(fileName)
            inputStream.bufferedReader().use { reader ->
                var line = reader.readLine()
                while (!line.isNullOrBlank()) {
                    line = line.replace("\"", "")
                    when(T::class) {
                        WordEntity::class -> {
                            val word = WordEntity.parseWordCSV(line)
                            items.add(word as T)
                        }
                        PhoneticEntity::class -> {
                            val phonetic = PhoneticEntity.parsePhoneticCSV(line)
                            items.add(phonetic as T)
                        }
                        MeaningEntity::class -> {
                            val meaning = MeaningEntity.parseMeaningCSV(line)
                            items.add(meaning as T)
                        }
                        DefinitionEntity::class -> {
                            val definition = DefinitionEntity.parseDefinitionCSV(line)
                            items.add(definition as T)
                        }

                    }
                    line = reader.readLine()
                }
            }

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