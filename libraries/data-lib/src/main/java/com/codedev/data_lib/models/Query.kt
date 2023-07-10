package com.codedev.data_lib.models

import android.graphics.Bitmap
import java.util.Date

data class Query(
    val title: String,
    val type: String,
    val isFromDB: Boolean = false
)
