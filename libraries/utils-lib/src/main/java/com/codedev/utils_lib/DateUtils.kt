package com.codedev.utils_lib

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    private val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    private val readableFormat = SimpleDateFormat("MMM dd, yyyy hh:mm:ss a", Locale.US)

    fun formatCurrentDateToString(): String {
        return formatDateToString(Date())
    }

    fun getCurrentDateInISOFormat(): String {
        return format.format(Date())
    }

    fun formatDateToString(date: Date) : String {
        return format.format(date)
    }

    fun parseDate(date: String): Date {
        return format.parse(date) ?: throw ParseException("Could not parse date $date", 0)
    }

    fun getReadableDateFromString(date: String): String {
        val d = parseDate(date)
        return readableFormat.format(d)
    }
}