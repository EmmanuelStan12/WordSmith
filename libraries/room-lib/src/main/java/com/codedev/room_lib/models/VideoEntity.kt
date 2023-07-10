package com.codedev.room_lib.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.codedev.room_lib.converters.TimestampConverter
import java.util.Date

@Entity(tableName = "video")
@TypeConverters(TimestampConverter::class)
data class VideoEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long? = null,
    val name: String,
    val duration: Int,
    val size: Int,
    val lastStop: Int,
    val lastAccessed: Date? = null,
    val folder: String,
    val path: String
)
