package com.codedev.room_lib._di

import android.content.Context
import androidx.room.Room
import com.codedev.room_lib.MediaDatabase
import com.codedev.room_lib.dao.QueryDao
import com.codedev.room_lib.dao.VideoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RoomModule {

    @Provides
    fun provideMediaDatabase(context: Context): MediaDatabase {
        return Room.databaseBuilder(
            context,
            MediaDatabase::class.java,
            "media_database"
        ).build()
    }

    @Provides
    fun provideVideoDao(database: MediaDatabase): VideoDao {
        return database.getVideoDao()
    }

    @Provides
    fun provideQueryDao(database: MediaDatabase): QueryDao {
        return database.getQueryDao()
    }
}