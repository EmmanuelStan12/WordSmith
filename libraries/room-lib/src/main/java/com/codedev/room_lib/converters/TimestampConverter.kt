package com.codedev.room_lib.converters

import androidx.room.TypeConverter
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val TIME_STAMP_FORMAT = "yyyy-MM-dd HH:mm:ss"

class TimestampConverter {
    private val df: DateFormat = SimpleDateFormat(TIME_STAMP_FORMAT, Locale.US)

    @TypeConverter
    fun fromTimestamp(value: String?): Date? {
        if (value != null) {
            try {
                return df.parse(value)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
        return null
    }

    @TypeConverter
    fun toTimestamp(value: Date?): String? {
        if (value != null) {
            try {
                return df.format(value)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
        return null
    }
}