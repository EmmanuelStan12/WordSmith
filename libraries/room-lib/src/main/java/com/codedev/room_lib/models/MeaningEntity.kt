package com.codedev.room_lib.models

import androidx.room.DatabaseView
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.google.gson.annotations.SerializedName

@Entity(tableName = "meaning")
data class MeaningEntity(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val meaning_id: Int? = null,
    val word: String? = null,
    val part_of_speech: String,
    @SerializedName("antonyms")
    val meaning_antonyms: String,
    @SerializedName("synonyms")
    val meaning_synonyms: String
)
