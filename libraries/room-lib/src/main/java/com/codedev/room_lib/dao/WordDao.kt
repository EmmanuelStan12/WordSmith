package com.codedev.room_lib.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.codedev.room_lib.models.MeaningEntity
import com.codedev.room_lib.models.PhoneticEntity
import com.codedev.room_lib.models.WordEntity

@Dao
interface WordDao {

    @Query("select * from word w limit :num")
    fun getWords(num: Int): List<WordEntity>

    @Query("select * from word w where w.name = :name")
    fun getWord(name: String): WordEntity

    @Query("select * from word w where w.name = :name")
    fun getWordByName(name: String): List<WordEntity>

    @Query("select * from word w where w.is_saved")
    fun getFavourites(): List<WordEntity>

    @Query("select * from word w limit 1 OFFSET :offset")
    fun getWordsWithOffset(offset: Int): List<WordEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(items: List<WordEntity>)

    @Update
    suspend fun updateWord(word: WordEntity)

    @Query("select * from word w inner join meaning m on w.name = m.word where w.name = :name")
    suspend fun getWordsWithMeaning(name: String): Map<WordEntity, List<MeaningEntity>>

    @Query("select * from phonetic p inner join word w on w.name = p.word where w.name = :name")
    suspend fun getWordsWithPhonetic(name: String): Map<WordEntity, List<PhoneticEntity>>

    @Query("select count(*) from word")
    suspend fun getTotalNumberOfWords(): Int

    @Query("select name from word where name like '%' || :query || '%' limit :limit")
    suspend fun searchWordsWithLimit(query: String, limit: Int): List<String>

    @Query("select * from word where name like '%' || :query || '%' limit :limit")
    suspend fun searchWordsWithLimitAndReturnType(query: String, limit: Int): List<WordEntity>

}
