package com.codedev.data_lib.models

import android.graphics.Bitmap
import java.io.Serializable
import java.util.Date

data class Video(
    val id: Long? = null,
    val name: String,
    val duration: Int,
    val size: Int,
    val lastStop: Int,
    val contentUri: String? = null,
    val lastAccessed: Date? = null,
    val folder: String,
    val path: String,
    val bitmap: Bitmap? = null
): Serializable
