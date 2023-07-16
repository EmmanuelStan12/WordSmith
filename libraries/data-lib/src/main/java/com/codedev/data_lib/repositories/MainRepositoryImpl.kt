package com.codedev.data_lib.repositories

import com.codedev.data_lib.models.Definition
import com.codedev.data_lib.models.Meaning
import com.codedev.data_lib.models.Word
import com.codedev.data_lib.repositories.interfaces.MainRepository
import com.codedev.data_lib.toDefinition
import com.codedev.data_lib.toMeaning
import com.codedev.data_lib.toPhonetic
import com.codedev.data_lib.toWord
import com.codedev.room_lib.dao.MeaningDao
import com.codedev.room_lib.dao.WordDao
import com.codedev.room_lib.models.DefinitionEntity
import com.codedev.room_lib.models.MeaningEntity
import com.codedev.room_lib.models.PhoneticEntity
import com.codedev.room_lib.models.WordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.random.Random

class MainRepositoryImpl(
    private val wordDao: WordDao,
    private val meaningDao: MeaningDao,
): MainRepository {

    override suspend fun getWordsByQuery(query: String): List<Word> = withContext(Dispatchers.IO) {
        TODO("Not yet implemented")
    }

    override suspend fun getMeaningsByWord(wordId: Int): List<Meaning> = withContext(Dispatchers.IO) {
        TODO("Not yet implemented")
    }

    override suspend fun getDefinitionsByMeaning(meaningId: Int): List<Definition> = withContext(Dispatchers.IO) {
        TODO("Not yet implemented")
    }

    override suspend fun getWordWithMetadata(wordId: Int) = withContext(Dispatchers.IO) {
        val meaningsDeferred = async { wordDao.getWordsWithMeaning(wordId) }
        val phoneticDeferred = async { wordDao.getWordsWithPhonetic(wordId) }
        val definitionDeferred = async { meaningDao.getDefinitionsWithMeaning(wordId) }

        val result = awaitAll(meaningsDeferred, phoneticDeferred, definitionDeferred)

        val r1 = result[0] as Map<WordEntity, List<MeaningEntity>>
        val f1 = r1.entries.first()
        val word = f1.key.toWord()

        val r2 = result[1] as Map<WordEntity, List<PhoneticEntity>>
        val f2 = r2.entries.first()
        val phonetics = f2.value.map { it.toPhonetic() }

        val r3 = result[2] as Map<MeaningEntity, List<DefinitionEntity>>
        val meanings = r3.entries.map { item ->
            val key = item.key.toMeaning()
            val values = item.value.map { it.toDefinition() }
            key.setDefinitions(values)

            key
        }

        word.setPhonetics(phonetics)
        word.setMeanings(meanings)

        word
    }

    override suspend fun getRandomWord(): Word? = withContext(Dispatchers.IO) {
        val total = wordDao.getTotalNumberOfWords()
        val wordId = wordDao.getWordsWithOffset(total, Random.nextInt(0, total))[0].word_id
            ?: return@withContext null

        val word = getWordWithMetadata(wordId)

        Timber.d(word.toString())

        word
    }
}