package com.codedev.data_lib.models

import java.util.Date

data class History(
    val word: Word,
    val date: Date? = Date(),
)
