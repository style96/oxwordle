package com.kodlamayabasla.wordle_oxford.backend.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.kodlamayabasla.wordle_oxford.UserPrefs
import com.kodlamayabasla.wordle_oxford.backend.repository.SettingsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: SettingsRepositoryImpl
    ) : BaseViewModel<SettingsViewModel.State>(State()) {
    data class State(
        val hardMode: Boolean = false,
        val darkTheme: Boolean = false,
        val highContrast: Boolean = false,
        val isLoading: Boolean = true
    )

    init {
        Log.d("SettingsViewModel","init")
        initFlow()
    }

    private fun initFlow() {
        Log.d("SettingsViewModel","initflow")
        viewModelScope.launch{
            userPreferencesRepository.fetchInitialPreferences().let {
                updateState {
                    copy(
                        hardMode = it.hardMode,
                        darkTheme = it.darkTheme,
                        highContrast = it.highContrast,
                        isLoading = false
                        )
                }
            }
        }
    }

    fun setHardMode(hardMode : Boolean){
        updateState { copy(hardMode = hardMode) }
    }
    fun setDarkTheme(darkTheme : Boolean){
        viewModelScope.launch { userPreferencesRepository.setDarkTheme(darkTheme) }
        updateState { copy(darkTheme = darkTheme) }
    }
    fun setHightContrast(highContrast : Boolean){
        updateState { copy(highContrast = highContrast) }
    }

}