package com.codedev.room_lib.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "query")
data class QueryEntity(
    @PrimaryKey(autoGenerate = false)
    val title: String,
    val type: String
)