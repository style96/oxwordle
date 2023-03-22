package com.kodlamayabasla.wordle_oxford.backend.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel

class SettingsViewModel : BaseViewModel<SettingsViewModel.State>(State()) {
    data class State(
        val hardMode: Boolean = false,
        val darkTheme: Boolean = false,
        val highContrast: Boolean = false,
    )

    fun setHardMode(hardMode : Boolean){
        updateState { copy(hardMode = hardMode) }
    }
    fun setDarkTheme(darkTheme : Boolean){
        updateState { copy(darkTheme = darkTheme) }
    }fun setHightContrast(highContrast : Boolean){
        updateState { copy(highContrast = highContrast) }
    }
}