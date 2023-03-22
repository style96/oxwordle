package com.kodlamayabasla.wordle_oxford.backend.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import dagger.Lazy

abstract class BaseViewModel<T> (
    private val initialState: T,
)  : ViewModel(){

    private val stateFlow = MutableStateFlow<T>(initialState)

    fun state(): StateFlow<T> = stateFlow

    internal fun updateState(newState: T.() -> T) {
        stateFlow.value = newState(stateFlow.value)
    }

    fun currentState(): T = state().value
}