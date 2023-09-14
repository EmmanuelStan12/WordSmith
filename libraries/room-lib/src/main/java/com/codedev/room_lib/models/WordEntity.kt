package com.codedev.room_lib.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.google.gson.annotations.SerializedName

@Entity(tableName = "word")
data class WordEntity(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val phonetic: String,
    val license: String,
    val origin: String,
    val source_urls: String,
    val is_saved: Boolean = false,
    val audio: String = ""
)
