package com.codedev.room_lib

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codedev.room_lib.dao.QueryDao
import com.codedev.room_lib.dao.VideoDao
import com.codedev.room_lib.models.QueryEntity
import com.codedev.room_lib.models.VideoEntity

@Database(entities = [VideoEntity::class, QueryEntity::class], version = 1)
abstract class MediaDatabase: RoomDatabase() {

    abstract fun getVideoDao(): VideoDao

    abstract fun getQueryDao(): QueryDao

    companion object {
        private var instance: MediaDatabase? = null

        fun getInstance(context: Context): MediaDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = buildMediaDatabase(context)
                }
            }
            return instance!!
        }

        private fun buildMediaDatabase(context: Context): MediaDatabase? {
            return Room.databaseBuilder(
                context,
                MediaDatabase::class.java,
                "media_database"
            ).build()
        }
    }

}