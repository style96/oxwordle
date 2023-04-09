package com.kodlamayabasla.wordle_oxford.backend.viewmodel

import com.kodlamayabasla.wordle_oxford.backend.repository.SettingsRepositoryImpl
import javax.inject.Inject

class StatisticsViewModel@Inject constructor(
    private val userPreferencesRepository: SettingsRepositoryImpl
) : BaseViewModel<StatisticsViewModel.State>(State()) {
    data class State(
        val gameCount : Int = 0,
    )



}