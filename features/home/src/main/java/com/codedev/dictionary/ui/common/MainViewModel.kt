package com.codedev.dictionary.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codedev.data_lib.Either
import com.codedev.data_lib.models.History
import com.codedev.data_lib.models.Word
import com.codedev.data_lib.repositories.interfaces.HistoryRepository
import com.codedev.data_lib.repositories.interfaces.MainRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val _events = MutableSharedFlow<HomeFragmentUIEvents>()
    val events get() = _events.asSharedFlow()

    private val _searchResults = MutableStateFlow<List<String>>(emptyList())
    val searchResults: StateFlow<List<String>>
        get() = _searchResults

    fun execute(action: HomeFragmentActions) {
        viewModelScope.launch {
            when (action) {
                is HomeFragmentActions.GetRandomWord -> {
                    getRandomWord()
                }
                is HomeFragmentActions.GetHistories -> {
                    getHistories(action.num)
                }

                is HomeFragmentActions.SearchWord -> {
                    searchWord(action.query)
                }

                is HomeFragmentActions.SearchWordWithReturnType -> {
                    searchWordWithReturnType(action.query)
                }

                is HomeFragmentActions.GetFavourites -> {
                    getFavourites()
                }

                else -> {}
            }
        }
    }

    private suspend fun getFavourites() {
        _events.emit(HomeFragmentUIEvents.GetFavouritesLoading)
        when (val result = repository.getFavourites()) {
            is Either.Failure -> {
                _events.emit(
                    HomeFragmentUIEvents.GetFavouritesResult(
                        null,
                        result.reason?.message ?: ""
                    )
                )
            }
            is Either.Success -> {
                _events.emit(HomeFragmentUIEvents.GetFavouritesResult(result.data, null))
            }
        }
    }

    private suspend fun getRandomWord() {
        _events.emit(HomeFragmentUIEvents.GetRandomWordLoading)
        when (val result = repository.getRandomWord()) {
            is Either.Failure -> {
                _events.emit(
                    HomeFragmentUIEvents.GetRandomWordResult(
                        null,
                        result.reason?.message ?: ""
                    )
                )
            }
            is Either.Success -> {
                _events.emit(HomeFragmentUIEvents.GetRandomWordResult(result.data, null))
            }
        }
    }

    private suspend fun getHistories(num: Int?) {
        _events.emit(HomeFragmentUIEvents.GetHistoriesLoading)
        val result: Either<List<History>, Exception> = if (num == null) {
            historyRepository.getHistoriesByDate()
        } else {
            historyRepository.getHistoriesByDateWithLimit(num)
        }
        when (result) {
            is Either.Failure -> {
                _events.emit(
                    HomeFragmentUIEvents.GetHistoriesResult(
                        null,
                        result.reason?.localizedMessage
                    )
                )
            }
            is Either.Success -> {
                _events.emit(HomeFragmentUIEvents.GetHistoriesResult(result.data, null))
            }
        }
    }

    private suspend fun searchWord(query: String) {
        val result = repository.searchWord(query)
        _searchResults.emit(result)
    }

    private suspend fun searchWordWithReturnType(query: String) {
        val result = repository.searchWordWithReturnType(query)
        _events.emit(HomeFragmentUIEvents.SearchWordResult(result))
    }
}

sealed interface HomeFragmentActions {

    object GetRandomWord: HomeFragmentActions
    data class GetHistories(val num: Int? = null): HomeFragmentActions
    data class SearchWord(val query: String): HomeFragmentActions
    data class SearchWordWithReturnType(val query: String): HomeFragmentActions
    object GetFavourites: HomeFragmentActions
}

sealed interface HomeFragmentUIEvents {

    object GetRandomWordLoading: HomeFragmentUIEvents
    data class GetRandomWordResult(
        val word: Word?,
        val exception: String?
    ): HomeFragmentUIEvents

    object GetHistoriesLoading: HomeFragmentUIEvents

    data class GetHistoriesResult(
        val histories: List<History>?,
        val exception: String?
    ): HomeFragmentUIEvents

    object GetFavouritesLoading: HomeFragmentUIEvents

    data class GetFavouritesResult(
        val words: List<Word>?,
        val exception: String?
    ): HomeFragmentUIEvents

    data class SearchWordResult(
        val words: List<Word>
    ): HomeFragmentUIEvents
}