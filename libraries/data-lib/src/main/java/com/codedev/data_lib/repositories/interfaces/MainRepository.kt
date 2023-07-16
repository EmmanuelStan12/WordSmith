package com.codedev.data_lib.repositories.interfaces

import com.codedev.data_lib.models.Definition
import com.codedev.data_lib.models.Meaning
import com.codedev.data_lib.models.Word

interface MainRepository {

    suspend fun getWordsByQuery(query: String): List<Word>

    suspend fun getMeaningsByWord(wordId: Int): List<Meaning>

    suspend fun getDefinitionsByMeaning(meaningId: Int): List<Definition>

    suspend fun getWordWithMetadata(wordId: Int): Word

    suspend fun getRandomWord(): Word?

}