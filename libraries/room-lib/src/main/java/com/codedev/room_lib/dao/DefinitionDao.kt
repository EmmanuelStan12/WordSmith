package com.codedev.room_lib.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codedev.room_lib.models.DefinitionEntity
import com.codedev.room_lib.models.WordEntity

@Dao
interface DefinitionDao {

    @Query("select * from definition d limit :num")
    fun getDefinition(num: Int): List<DefinitionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDefinitions(items: List<DefinitionEntity>)

}
