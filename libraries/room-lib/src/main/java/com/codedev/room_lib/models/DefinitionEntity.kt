package com.codedev.room_lib.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "definition")
data class DefinitionEntity(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val definition_id: Int? = null,
    @SerializedName("example")
    val definition_example: String,
    @SerializedName("antonyms")
    val definition_antonyms: String,
    @SerializedName("synonyms")
    val definition_synonyms: String,
    val definition: String,
    val meaning_id: Int? = null
)