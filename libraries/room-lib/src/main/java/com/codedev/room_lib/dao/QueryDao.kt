package com.codedev.room_lib.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codedev.room_lib.models.QueryEntity

@Dao
interface QueryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuery(query: QueryEntity): Long

    @Query("SELECT * FROM `query` WHERE type = :type")
    fun getQueriesByType(type: String): List<QueryEntity>

    @Query("SELECT * FROM `query` WHERE type = :type AND title LIKE '%' || :title || '%'")
    fun getQueryBySearch(type: String, title: String): List<QueryEntity>

    @Delete
    suspend fun deleteQuery(query: QueryEntity)

}