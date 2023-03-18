package com.kodlamayabasla.wordle_oxford.backend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kodlamayabasla.wordle_oxford.backend.repository.LevelRepository
import com.kodlamayabasla.wordle_oxford.backend.usecase.GetNextLevel
import com.kodlamayabasla.wordle_oxford.backend.usecase.ResetLevels

class LevelsViewModelFactory(private val levelRepository: LevelRepository,
                                private val getNextLevel: GetNextLevel,
                                private val resetLevels: ResetLevels)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(LevelsViewModel::class.java))
            return LevelsViewModel(levelRepository,getNextLevel,resetLevels) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}