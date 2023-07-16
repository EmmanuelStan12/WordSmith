package com.codedev.room_lib.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codedev.room_lib.models.DefinitionEntity
import com.codedev.room_lib.models.MeaningEntity
import com.codedev.room_lib.models.WordEntity

@Dao
interface MeaningDao {

    @Query("select * from meaning m limit :num")
    fun getMeaning(num: Int): List<MeaningEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeanings(items: List<MeaningEntity>)

    @Query("select * from meaning m inner join definition d on d.meaning_id = m.meaning_id where m.word_id = :wordId")
    suspend fun getDefinitionsWithMeaning(wordId: Int): Map<MeaningEntity, List<DefinitionEntity>>

}
