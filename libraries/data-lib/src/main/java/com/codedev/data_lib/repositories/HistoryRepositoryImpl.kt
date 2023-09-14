package com.codedev.data_lib.repositories

import com.codedev.data_lib.Either
import com.codedev.data_lib.models.History
import com.codedev.data_lib.repositories.interfaces.HistoryRepository
import com.codedev.data_lib.toHistoryEntity
import com.codedev.data_lib.toWord
import com.codedev.room_lib.dao.HistoryDao
import com.codedev.room_lib.dao.WordDao
import com.codedev.room_lib.models.HistoryEntity
import com.codedev.room_lib.models.WordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HistoryRepositoryImpl(
    private val historyDao: HistoryDao
): HistoryRepository {

    override suspend fun getHistoriesByDateWithLimit(limit: Int): Either<List<History>, Exception> = withContext(Dispatchers.IO) {
        try {
            val histories = historyDao.getHistoriesByDateWithLimit(limit)
            val mappedHistories = histories.entries.map { entry: Map.Entry<HistoryEntity, List<WordEntity>> ->
                History(entry.value[0].toWord(), entry.key.date)
            }
            Either.Success(mappedHistories)
        } catch (e: Exception) {
            Either.Failure(e)
        }
    }

    override suspend fun getHistoriesByDate(): Either<List<History>, Exception> = withContext(Dispatchers.IO) {
        try {
            val histories = historyDao.getHistoriesByDate()
            val mappedHistories = histories.entries.map { entry: Map.Entry<HistoryEntity, List<WordEntity>> ->
                History(entry.value[0].toWord(), entry.key.date)
            }
            Either.Success(mappedHistories)
        } catch (e: Exception) {
            Either.Failure(e)
        }
    }

    override suspend fun insertHistory(name: String): Either<String?, Exception> = withContext(Dispatchers.IO) {
        try {
            historyDao.insertHistory(name.toHistoryEntity())
            Either.Success(name)
        } catch (e: Exception) {
            Either.Failure(e)
        }
    }

    override suspend fun deleteHistory(history: History): Either<String?, Exception> = withContext(Dispatchers.IO) {
        try {
            historyDao.deleteHistory(history.toHistoryEntity())
            Either.Success(history.word.name)
        } catch (e: Exception) {
            Either.Failure(e)
        }
    }
}