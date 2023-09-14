package com.codedev.data_lib.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Phonetic(
    val phonetic_id: Int? = null,
    val text: String,
    val audio: String,
    val word: String,
    val source_url: String
)
