package com.codedev.data_lib.repositories.interfaces

import com.codedev.data_lib.Either
import com.codedev.data_lib.content_provider.ContentProviderVideo
import com.codedev.data_lib.models.Folder
import com.codedev.data_lib.models.Query
import com.codedev.data_lib.models.Video

interface IVideoRepository {

    suspend fun saveVideos(contentProviderVideos: List<ContentProviderVideo>): Either<String?, Exception>

    suspend fun getVideosByFolder(folder: String): Either<List<Video>, Exception>

    suspend fun searchVideosByFolder(folder: String, searchQuery: String): Either<List<Video>, Exception>

    suspend fun searchVideosByFolderWithSearchQuery(folder: String, searchQuery: String): Either<List<Video>, Exception>

    suspend fun getQueriesBySearch(type: String, title: String, folder: String? = null): Either<List<Query>, Exception>

    suspend fun insertQuery(type: String, title: String): Either<String?, Exception>

    suspend fun deleteQuery(query: Query): Either<String?, Exception>

}