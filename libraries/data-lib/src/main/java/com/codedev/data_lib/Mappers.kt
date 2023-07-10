package com.codedev.data_lib

import com.codedev.data_lib.content_provider.ContentProviderVideo
import com.codedev.data_lib.models.Query
import com.codedev.data_lib.models.Video
import com.codedev.room_lib.models.QueryEntity
import com.codedev.room_lib.models.VideoEntity
import java.util.Date

fun VideoEntity.toVideo() = Video(
    id, name, duration, size, lastStop, null, lastAccessed, folder, path
)

fun Video.toVideoEntity() = VideoEntity(
    id, name, duration, size, lastStop, lastAccessed, folder, path
)

fun ContentProviderVideo.toVideoEntity() = VideoEntity(
    id, name, duration, size, 0, Date(), folder, path
)

fun QueryEntity.toQuery() = Query(title, type, true)

fun Query.toQueryEntity() = QueryEntity(title, type)

fun VideoEntity.toQuery() = Query(title = name, type = "video", false)