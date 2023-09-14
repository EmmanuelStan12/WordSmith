package com.codedev.room_lib.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codedev.room_lib.models.HistoryEntity
import com.codedev.room_lib.models.WordEntity

@Dao
interface HistoryDao {

    @Query("select * from history")
    suspend fun getHistories(): List<HistoryEntity>

    @Query("select * from history limit :limit")
    suspend fun getHistoriesWithLimit(limit: Int): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: HistoryEntity)

    @Delete
    suspend fun deleteHistory(history: HistoryEntity)

    @Query("select * from history h inner join word w on w.name = h.word order by date desc limit :limit")
    suspend fun getHistoriesByDateWithLimit(limit: Int): Map<HistoryEntity, List<WordEntity>>

    @Query("select * from history h inner join word w on w.name = h.word order by date desc")
    suspend fun getHistoriesByDate(): Map<HistoryEntity, List<WordEntity>>
}