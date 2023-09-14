package com.codedev.data_lib.repositories.interfaces

import com.codedev.data_lib.Either
import com.codedev.data_lib.models.Definition
import com.codedev.data_lib.models.Meaning
import com.codedev.data_lib.models.Word

interface MainRepository {

    suspend fun getWordsByQuery(query: String): List<Word>

    suspend fun getMeaningsByWord(wordId: Int): List<Meaning>

    suspend fun getDefinitionsByMeaning(meaningId: Int): List<Definition>

    suspend fun getWordWithMetadata(name: String): Word

    suspend fun getRandomWord(): Either<Word?, Exception>

    suspend fun searchWord(query: String): List<String>
    suspend fun searchWordWithReturnType(query: String): List<Word>

    suspend fun getWord(str: String): Either<Word, Exception>

    suspend fun updateWord(word: Word): Either<Boolean, Exception>

    suspend fun getFavourites(): Either<List<Word>, Exception>

}