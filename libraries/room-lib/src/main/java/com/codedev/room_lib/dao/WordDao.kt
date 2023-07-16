package com.codedev.room_lib.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codedev.room_lib.models.MeaningEntity
import com.codedev.room_lib.models.PhoneticEntity
import com.codedev.room_lib.models.WordEntity

@Dao
interface WordDao {

    @Query("select * from word w limit :num")
    fun getWords(num: Int): List<WordEntity>

    @Query("select * from word w limit :num OFFSET :offset")
    fun getWordsWithOffset(num: Int, offset: Int): List<WordEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(items: List<WordEntity>)

    @Query("select * from word w inner join meaning m on w.word_id = m.word_id where w.word_id = :wordId")
    suspend fun getWordsWithMeaning(wordId: Int): Map<WordEntity, List<MeaningEntity>>

    @Query("select * from phonetic p inner join word w on w.word_id = p.word_id where w.word_id = :wordId")
    suspend fun getWordsWithPhonetic(wordId: Int): Map<WordEntity, List<PhoneticEntity>>

    @Query("select count(*) from word")
    suspend fun getTotalNumberOfWords(): Int

}
