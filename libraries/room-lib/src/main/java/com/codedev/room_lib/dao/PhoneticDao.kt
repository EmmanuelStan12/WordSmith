package com.codedev.room_lib.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codedev.room_lib.models.MeaningEntity
import com.codedev.room_lib.models.PhoneticEntity
import com.codedev.room_lib.models.WordEntity

@Dao
interface PhoneticDao {

    @Query("select * from phonetic p limit :num")
    fun getPhonetic(num: Int): List<PhoneticEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhonetics(items: List<PhoneticEntity>)
}
