package com.codedev.room_lib

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codedev.room_lib.dao.DefinitionDao
import com.codedev.room_lib.dao.MeaningDao
import com.codedev.room_lib.dao.PhoneticDao
import com.codedev.room_lib.dao.WordDao
import com.codedev.room_lib.models.DefinitionEntity
import com.codedev.room_lib.models.MeaningEntity
import com.codedev.room_lib.models.PhoneticEntity
import com.codedev.room_lib.models.WordEntity
import com.codedev.room_lib.utils.DictionaryFileLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber


@Database(entities = [DefinitionEntity::class, MeaningEntity::class, PhoneticEntity::class, WordEntity::class], version = 1)
abstract class DictionaryDatabase: RoomDatabase() {

    abstract fun getWordDao(): WordDao
    abstract fun getPhoneticDao(): PhoneticDao
    abstract fun getMeaningDao(): MeaningDao
    abstract fun getDefinitionDao(): DefinitionDao

    companion object {
        private var instance: DictionaryDatabase? = null

        fun getInstance(context: Context): DictionaryDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = buildDictionaryDatabase(context)
                }
            }
            return instance!!
        }

        private fun buildDictionaryDatabase(context: Context): DictionaryDatabase? {
            val databaseBuilder = Room.databaseBuilder(
                context,
                DictionaryDatabase::class.java,
                "words_db"
            )

            return databaseBuilder.build()
        }

        suspend fun performInitialDBOperations(database: DictionaryDatabase, context: Context): Pair<Exception?, Boolean> = withContext(Dispatchers.IO) {
                val items = withContext(Dispatchers.Default) {
                    instance?.getWordDao()?.getWords(1)
                }
                if (!items.isNullOrEmpty()) {
                    return@withContext Pair(null, true)
                }

                try {
                    val wordCSV = async { DictionaryFileLoader.loadCSVFromAsset<WordEntity>("word.csv", context) }
                    val phoneticCSV = async { DictionaryFileLoader.loadCSVFromAsset<PhoneticEntity>("phonetic.csv", context) }
                    val meaningCSV = async { DictionaryFileLoader.loadCSVFromAsset<MeaningEntity>("meaning.csv", context) }
                    val definitionCSV = async { DictionaryFileLoader.loadCSVFromAsset<DefinitionEntity>("definition.csv", context) }

                    val result = awaitAll(wordCSV, phoneticCSV, meaningCSV, definitionCSV)

                    val words: List<WordEntity>? = result[0] as? List<WordEntity>
                    val phonetics: List<PhoneticEntity>? = result[1] as? List<PhoneticEntity>
                    val meanings: List<MeaningEntity>? = result[2] as? List<MeaningEntity>
                    val definitions: List<DefinitionEntity>? = result[3] as? List<DefinitionEntity>

                    listOf(
                        launch { instance?.getWordDao()?.insertWords(words ?: emptyList()) },
                        launch { instance?.getPhoneticDao()?.insertPhonetics(phonetics ?: emptyList()) },
                        launch { instance?.getMeaningDao()?.insertMeanings(meanings ?: emptyList()) },
                        launch { instance?.getDefinitionDao()?.insertDefinitions(definitions ?: emptyList()) }
                    ).joinAll()

                    return@withContext Pair(null, true)

                } catch (e: Exception) {
                    Timber.e(e)
                    return@withContext Pair(e, false)
                }
        }
    }

}