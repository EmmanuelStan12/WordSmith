package com.codedev.data_lib._di

import com.codedev.data_lib.repositories.MainRepositoryImpl
import com.codedev.data_lib.repositories.interfaces.MainRepository
import com.codedev.room_lib.dao.MeaningDao
import com.codedev.room_lib.dao.WordDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataModule {

    @Provides
    @Singleton
    fun provideMainRepository(
        wordDao: WordDao,
        meaningDao: MeaningDao
    ): MainRepository {
        return MainRepositoryImpl(wordDao, meaningDao)
    }
}