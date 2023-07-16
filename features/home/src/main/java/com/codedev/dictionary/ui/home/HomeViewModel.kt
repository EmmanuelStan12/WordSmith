package com.codedev.dictionary.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codedev.data_lib.models.Word
import com.codedev.data_lib.repositories.interfaces.MainRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _randomWord = MutableStateFlow<Word?>(null)
    val randomWord get() = _randomWord.asStateFlow()

    private val _events = MutableSharedFlow<HomeFragmentUIEvents>()
    val events get() = _events.asSharedFlow()

    fun execute(action: HomeFragmentActions) {
        viewModelScope.launch {
            when (action) {
                is HomeFragmentActions.GetRandomWord -> {
                    getRandomWord()
                }
            }
        }
    }

    private suspend fun getRandomWord() {
        _events.emit(HomeFragmentUIEvents.LoadingStarted)
        delay(2000L)
        val word = repository.getRandomWord()
        _randomWord.emit(word)
        _events.emit(HomeFragmentUIEvents.LoadingStopped)
    }
}

sealed interface HomeFragmentActions {

    object GetRandomWord: HomeFragmentActions
}

sealed interface HomeFragmentUIEvents {

    object LoadingStarted: HomeFragmentUIEvents
    object LoadingStopped: HomeFragmentUIEvents
}