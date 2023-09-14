package com.codedev.room_lib.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "phonetic")
data class PhoneticEntity(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val phonetic_id: Int? = null,
    val word: String,
    val text: String,
    val audio: String,
    val source_url: String
)