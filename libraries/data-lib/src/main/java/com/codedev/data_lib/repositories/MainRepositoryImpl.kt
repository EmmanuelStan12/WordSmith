package com.codedev.data_lib.repositories

import com.codedev.data_lib.Either
import com.codedev.data_lib.models.Definition
import com.codedev.data_lib.models.Meaning
import com.codedev.data_lib.models.Word
import com.codedev.data_lib.repositories.interfaces.MainRepository
import com.codedev.data_lib.toDefinition
import com.codedev.data_lib.toMeaning
import com.codedev.data_lib.toPhonetic
import com.codedev.data_lib.toWord
import com.codedev.data_lib.toWordEntity
import com.codedev.room_lib.dao.MeaningDao
import com.codedev.room_lib.dao.WordDao
import com.codedev.room_lib.models.DefinitionEntity
import com.codedev.room_lib.models.MeaningEntity
import com.codedev.room_lib.models.PhoneticEntity
import com.codedev.room_lib.models.WordEntity
import kotlinx.coroutines.Deferred
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

    override suspend fun getWordWithMetadata(name: String) = withContext(Dispatchers.IO) {
        val meaningsDeferred = async { wordDao.getWordsWithMeaning(name) }
        val phoneticDeferred = async { wordDao.getWordsWithPhonetic(name) }
        val definitionDeferred = async { meaningDao.getDefinitionsWithMeaning(name) }

        val result = awaitAll(meaningsDeferred, phoneticDeferred, definitionDeferred)

        val r1 = result[0] as Map<WordEntity, List<MeaningEntity>>
        val f1 = r1.entries.first()
        val word = f1.key.toWord()

        val r2 = result[1] as Map<WordEntity, List<PhoneticEntity>>
        if (r2.isNotEmpty()) {
            val f2 = r2.entries.first()
            val phonetics = f2.value.map { it.toPhonetic() }
            word.setPhonetics(phonetics)
        }

        val r3 = result[2] as Map<MeaningEntity, List<DefinitionEntity>>
        Timber.d("map - ${r3.values}")
        val meanings = r3.entries.map { item ->
            val key = item.key.toMeaning()
            key.word = word.name
            val values = item.value.map { it.toDefinition() }
            key.setDefinitions(values)

            Timber.d("values - ${key.definitions}")

            key
        }

        word.setMeanings(meanings)

        word
    }

    override suspend fun getRandomWord(): Either<Word?, Exception> = withContext(Dispatchers.IO) {
        try {
            val total = wordDao.getTotalNumberOfWords()
            val name = wordDao.getWordsWithOffset(Random.nextInt(0, total - 1))[0].name
                ?: return@withContext Either.Failure(Exception("Cannot find word"))

            val word = getWordWithMetadata(name)

            Timber.d(word.toString())
            Timber.d(word.phonetics.toString())
            Timber.d(word.meanings.toString())

            Either.Success(word)
        }catch (e: Exception) {
            Either.Failure(e)
        }
    }

    override suspend fun searchWord(query: String): List<String> = withContext(Dispatchers.IO) {
        wordDao.searchWordsWithLimit(query, 10)
    }

    override suspend fun searchWordWithReturnType(query: String): List<Word> = withContext(Dispatchers.IO) {
        wordDao.searchWordsWithLimitAndReturnType(query, 20).map { it.toWord() }
    }

    override suspend fun getWord(str: String): Either<Word, Exception> = withContext(Dispatchers.IO) {
        try {
            val words = wordDao.getWordByName(str)
            if (words.isNotEmpty()) {
                val word = getWordWithMetadata(words[0].name)
                return@withContext Either.Success(word)
            }
            return@withContext Either.Failure(Exception(str))
        }catch (e: Exception) {
            Either.Failure(e)
        }
    }

    override suspend fun updateWord(word: Word): Either<Boolean, Exception> = withContext(Dispatchers.IO) {
        try {
            wordDao.updateWord(word.toWordEntity())
            return@withContext Either.Success(true)
        }catch (e: Exception) {
            Either.Failure(e)
        }
    }

    override suspend fun getFavourites(): Either<List<Word>, Exception> = withContext(Dispatchers.IO) {
        try {
            val words = wordDao.getFavourites().map { it.toWord() }
            if (words.isNotEmpty()) {
                return@withContext Either.Success(words)
            }
            return@withContext Either.Failure(Exception("An unknown error occurred"))
        }catch (e: Exception) {
            Either.Failure(e)
        }
    }
}