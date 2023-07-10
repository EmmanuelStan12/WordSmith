package com.codedev.data_lib.content_provider

data class ContentProviderVideo(
    val id: Long? = null,
    val name: String,
    val duration: Int,
    val size: Int,
    val contentUri: String,
    val folder: String,
    val path: String
)
