package com.codedev.room_lib.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.codedev.room_lib.converters.TimestampConverter
import java.util.Date

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = false)
    val word: String,
    var date: Date? = null
)
