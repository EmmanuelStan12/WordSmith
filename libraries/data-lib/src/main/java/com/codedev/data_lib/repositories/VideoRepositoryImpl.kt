package com.codedev.data_lib.repositories

import com.codedev.data_lib.Either
import com.codedev.data_lib.content_provider.ContentProviderVideo
import com.codedev.data_lib.domain.VideoDomainOperations
import com.codedev.data_lib.models.Query
import com.codedev.data_lib.models.Video
import com.codedev.data_lib.repositories.interfaces.IVideoRepository
import com.codedev.data_lib.toQuery
import com.codedev.data_lib.toQueryEntity
import com.codedev.data_lib.toVideo
import com.codedev.data_lib.toVideoEntity
import com.codedev.room_lib.dao.QueryDao
import com.codedev.room_lib.dao.VideoDao
import com.codedev.room_lib.models.QueryEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.StringBuilder

class VideoRepositoryImpl(
    private val videoDao: VideoDao,
    private val queryDao: QueryDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): IVideoRepository {

    private var currentFolder: String? = null
    private var currentFolderVideos: List<Video>? = null

    override suspend fun saveVideos(contentProviderVideos: List<ContentProviderVideo>): Either<String?, Exception> =
        withContext(dispatcher) {
            val dbVideos = videoDao.getAllVideos()

            if (dbVideos.isEmpty()) {
                videoDao.insertVideos(
                    contentProviderVideos.map {
                        it.toVideoEntity()
                    }
                )
                return@withContext Either.Success(null)
            }

            try {

                VideoDomainOperations.initialize(
                    dbEntities = dbVideos.sortedBy { it.id },
                    contentProviderEntities = contentProviderVideos.sortedBy { it.id },
                    insert = { video ->
                        videoDao.insertVideo(video.toVideoEntity())
                    },
                    update = { video, query ->
                        val builder = StringBuilder()
                        builder.append("UPDATE video SET ")
                        builder.append("$query ")
                        builder.append("WHERE video.id = ${video.id}")

                        videoDao.updateVideos(builder.toString())
                    },
                    delete = {

                    }
                )

                return@withContext Either.Success(null)
            } catch(e: Exception) {
                return@withContext Either.Failure(e)
            }
        }

    override suspend fun getVideosByFolder(folder: String): Either<List<Video>, Exception> =
        withContext(Dispatchers.IO){
            if (folder.equals(currentFolder, true) && !currentFolderVideos.isNullOrEmpty()) {
                return@withContext Either.Success(currentFolderVideos!!)
            }
            val videos = videoDao.getVideosByFolder(folder)
                .map { it.toVideo() }
            currentFolder = folder
            currentFolderVideos = videos
            Either.Success(videos)
        }

    override suspend fun searchVideosByFolder(
        folder: String,
        searchQuery: String
    ): Either<List<Video>, Exception> =
        withContext(Dispatchers.IO) {
            val videos = videoDao.getVideosBySearchQuery(searchQuery)
            Either.Success(videos.map { it.toVideo() })
        }

    override suspend fun searchVideosByFolderWithSearchQuery(
        folder: String,
        searchQuery: String
    ): Either<List<Video>, Exception> = withContext(Dispatchers.IO) {
        val videos = videoDao.getVideosByFolderWithSearchQuery(folder, searchQuery)
        Either.Success(videos.map { it.toVideo() })
    }

    override suspend fun getQueriesBySearch(
        type: String,
        title: String,
        folder: String?
    ): Either<List<Query>, Exception> =
        withContext(Dispatchers.IO){
            val queries = queryDao.getQueryBySearch(type, title)

            if (queries.isEmpty() && folder != null) {
                val videos = videoDao.getVideosByFolderWithSearchQuery(folder, title)
                val videoQueries = videos.map { it.toQuery() }
                return@withContext Either.Success(videoQueries)
            }

            Either.Success(queries.map {
                it.toQuery()
            })
        }

    override suspend fun insertQuery(type: String, title: String): Either<String?, Exception> =
        withContext(Dispatchers.IO) {
            val query = QueryEntity(title, type)
            val index = queryDao.insertQuery(query)
            if (index.toInt() == -1)
                return@withContext Either.Failure(Exception("Failed"))
            return@withContext Either.Success(null)
        }

    override suspend fun deleteQuery(query: Query): Either<String?, Exception> = withContext(Dispatchers.IO) {
        val entity = query.toQueryEntity()
        val index = queryDao.deleteQuery(entity)
        return@withContext Either.Success(null)
    }
}