package com.codedev.data_lib.domain

import com.codedev.data_lib.content_provider.ContentProviderVideo
import com.codedev.data_lib.models.Folder
import com.codedev.room_lib.models.VideoEntity
import java.lang.StringBuilder

object VideoDomainOperations {

    suspend fun initialize(
        dbEntities: List<VideoEntity>,
        contentProviderEntities: List<ContentProviderVideo>,
        insert: suspend (ContentProviderVideo) -> Unit,
        update: suspend (ContentProviderVideo, String) -> Unit,
        delete: suspend (VideoEntity) -> Unit
    ) {

        var i = 0

        for (contentProviderEntity in contentProviderEntities) {

            val videoEntity = dbEntities.getOrNull(i)

            val areItemsSame = areItemsTheSame(videoEntity, contentProviderEntity)

            if (!areItemsSame) {
                insert(contentProviderEntity)
                i++
                continue
            }

            val query = areContentsTheSame(videoEntity, contentProviderEntity)

            if (!query.isNullOrBlank()) {
                update(contentProviderEntity, query)
                i++
                continue
            }
        }

        for (n in i until dbEntities.size) {
            delete(dbEntities[n])
        }
    }

    private fun areItemsTheSame(oldEntity: VideoEntity?, newEntity: ContentProviderVideo?): Boolean {
        if (oldEntity == null || newEntity == null)
            return false
        return oldEntity.id == newEntity.id
    }

    private fun areContentsTheSame(oldEntity: VideoEntity?, newEntity: ContentProviderVideo?): String? {
        if (oldEntity == null || newEntity == null)
            return null
        val builder = StringBuilder()
        when {
            oldEntity.name != newEntity.name -> {
                builder.append("name = :${newEntity.name}")
            }
            else -> {}
        }

        return builder.toString()
    }
}