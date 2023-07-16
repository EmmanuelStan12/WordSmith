package com.codedev.room_lib._di

import android.content.Context
import com.codedev.room_lib.DictionaryDatabase
import com.codedev.room_lib.dao.DefinitionDao
import com.codedev.room_lib.dao.MeaningDao
import com.codedev.room_lib.dao.PhoneticDao
import com.codedev.room_lib.dao.WordDao
import dagger.Module
import dagger.Provides

@Module
object RoomModule {

    @Provides
    fun provideMediaDatabase(context: Context): DictionaryDatabase {
        return DictionaryDatabase.getInstance(context)
    }

    @Provides
    fun provideWordDao(database: DictionaryDatabase): WordDao {
        return database.getWordDao()
    }

    @Provides
    fun providePhoneticDao(database: DictionaryDatabase): PhoneticDao {
        return database.getPhoneticDao()
    }

    @Provides
    fun provideMeaningDao(database: DictionaryDatabase): MeaningDao {
        return database.getMeaningDao()
    }

    @Provides
    fun provideDefinitionDao(database: DictionaryDatabase): DefinitionDao {
        return database.getDefinitionDao()
    }
}