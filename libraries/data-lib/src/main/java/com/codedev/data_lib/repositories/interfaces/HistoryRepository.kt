package com.codedev.data_lib.repositories.interfaces

import com.codedev.data_lib.Either
import com.codedev.data_lib.models.History

interface HistoryRepository {

    suspend fun getHistoriesByDateWithLimit(limit: Int): Either<List<History>, Exception>

    suspend fun getHistoriesByDate(): Either<List<History>, Exception>

    suspend fun insertHistory(name: String): Either<String?, Exception>

    suspend fun deleteHistory(history: History): Either<String?, Exception>

}