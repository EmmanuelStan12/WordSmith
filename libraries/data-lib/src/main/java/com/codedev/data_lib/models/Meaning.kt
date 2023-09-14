package com.codedev.data_lib.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Meaning(
    val meaning_id: Int? = null,
    val part_of_speech: String,
    val meaning_antonyms: String,
    val meaning_synonyms: String,
    var word: String? = null
) {
    var definitions: List<Definition>? = null
        private set

    fun setDefinitions(definitions: List<Definition>) {
        this.definitions = definitions
    }
}
