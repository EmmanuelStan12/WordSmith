package com.codedev.room_lib.dao

import android.content.ContentProvider
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.codedev.room_lib.models.VideoEntity

@Dao
interface VideoDao {

    @Insert
    suspend fun insertVideo(video: VideoEntity): Long

    @Query("SELECT * FROM video")
    fun getAllVideos(): List<VideoEntity>

    @Query("SELECT * FROM video WHERE folder = :folder")
    fun getVideosByFolder(folder: String): List<VideoEntity>

    @Query("SELECT * FROM video WHERE folder = :folder AND (name LIKE '%' || :searchQuery || '%')")
    fun getVideosByFolderWithSearchQuery(folder: String, searchQuery: String): List<VideoEntity>

    @Query("SELECT * FROM video WHERE (name LIKE '%' || :searchQuery || '%' OR folder LIKE '%' || :searchQuery || '%')")
    fun getVideosBySearchQuery(searchQuery: String): List<VideoEntity>

    @Insert
    suspend fun insertVideos(videos: List<VideoEntity>): List<Long>

    @Query("UPDATE video SET lastStop = :lastStop WHERE id = :id")
    suspend fun updateVideoLastStop(lastStop: Int, id: Int): Int

    @Query("UPDATE video SET lastAccessed = :lastAccess WHERE id = :id")
    suspend fun updateVideoLastAccess(lastAccess: String, id: Int): Int

    @Query("UPDATE video SET name = :name WHERE id = :id")
    suspend fun updateVideoName(name: String, id: Int): Int

    @RawQuery
    fun updateVideos(query: SupportSQLiteQuery): Int

    fun updateVideos(query: String): Int {
        val sqlQuery = SimpleSQLiteQuery(query)

        return updateVideos(sqlQuery)
    }

}
