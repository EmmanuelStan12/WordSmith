package com.codedev.data_lib.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Word(
    val word_id: Int? = null,
    val name: String? = null,
    val phonetic: String,
    val license: String,
    val origin: String,
    val source_urls: String,
) {
    var meanings: List<Meaning>? = null
        private set
    var phonetics: List<Phonetic>? = null
        private set

    fun setMeanings(meanings: List<Meaning>) {
        this.meanings = meanings
    }

    fun setPhonetics(phonetic: List<Phonetic>) {
        this.phonetics = phonetics
    }
}
