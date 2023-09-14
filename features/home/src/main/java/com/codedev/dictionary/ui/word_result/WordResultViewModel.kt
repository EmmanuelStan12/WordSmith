package com.codedev.dictionary.ui.word_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codedev.data_lib.Either
import com.codedev.data_lib.models.History
import com.codedev.data_lib.models.Word
import com.codedev.data_lib.repositories.interfaces.HistoryRepository
import com.codedev.data_lib.repositories.interfaces.MainRepository
import com.codedev.dictionary.ui.common.HomeFragmentActions
import com.codedev.dictionary.ui.common.HomeFragmentUIEvents
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class WordResultViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val historyRepository: HistoryRepository
): ViewModel() {

    private val _events = MutableSharedFlow<WordResultFragmentUIEvents>()
    val events get() = _events.asSharedFlow()

    fun execute(action: WordResultFragmentActions) {
        viewModelScope.launch {
            when(action) {
                is WordResultFragmentActions.GetWord -> {
                    getWord(action.word)
                }

                is WordResultFragmentActions.UpdateWord -> {
                    updateWord(word = action.word)
                }

                is WordResultFragmentActions.InsertHistory -> {
                    insertHistory(action.name)
                }
            }
        }
    }

    private suspend fun getWord(word: String) {
        _events.emit(WordResultFragmentUIEvents.GetWordLoading)
        val result: Either<Word, Exception> = mainRepository.getWord(word)
        when (result) {
            is Either.Failure -> {
                _events.emit(WordResultFragmentUIEvents.GetWordResult(null, result.reason?.localizedMessage))
            }
            is Either.Success -> {
                _events.emit(WordResultFragmentUIEvents.GetWordResult(result.data, null))
            }
        }
    }

    private suspend fun updateWord(word: Word) {
        val result: Either<Boolean, Exception> = mainRepository.updateWord(word)
        when (result) {
            is Either.Failure -> {
                Timber.e(result.reason)
            }
            is Either.Success -> {
                _events.emit(WordResultFragmentUIEvents.UpdateWordSuccess(word))
            }
        }
    }

    private suspend fun insertHistory(name: String) {
        historyRepository.insertHistory(name)
    }
}


sealed interface WordResultFragmentActions {

    data class GetWord(val word: String): WordResultFragmentActions

    data class UpdateWord(val word: Word): WordResultFragmentActions

    data class InsertHistory(val name: String): WordResultFragmentActions
}

sealed interface WordResultFragmentUIEvents {

    object GetWordLoading: WordResultFragmentUIEvents
    data class GetWordResult(
        val word: Word?,
        val exception: String?
    ): WordResultFragmentUIEvents

    data class UpdateWordSuccess(val word: Word): WordResultFragmentUIEvents
    data class UpdateWordError(val word: Word): WordResultFragmentUIEvents
}