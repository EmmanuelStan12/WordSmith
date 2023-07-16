package com.codedev.data_lib.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Definition(
    val definition_id: Int? = null,
    val definition_example: String,
    val definition_antonyms: String,
    val definition_synonyms: String,
    val definition: String,
    val meaning_id: Int? = null,
)
