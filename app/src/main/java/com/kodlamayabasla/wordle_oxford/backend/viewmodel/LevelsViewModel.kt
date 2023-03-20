package com.kodlamayabasla.wordle_oxford.backend.viewmodel

import android.util.Log
import com.kodlamayabasla.wordle_oxford.backend.models.Level
import com.kodlamayabasla.wordle_oxford.backend.repository.LevelRepository
import com.kodlamayabasla.wordle_oxford.backend.usecase.GetNextLevel
import com.kodlamayabasla.wordle_oxford.backend.usecase.ResetLevels

class LevelsViewModel(
    private val levelRepository: LevelRepository,
    private val getNextLevel: GetNextLevel,
    private val resetLevels: ResetLevels,
) : BaseViewModel<LevelsViewModel.State>(State()) {
    data class State(
        val currentLevel: Level? = null,
        val lastLevelReached: Boolean = false,
        )

    init {
        updateLevel()
    }

    fun levelPassed() {
        currentState().currentLevel?.let { levelRepository.levelPassed(it) }
        updateLevel()
    }

    private fun updateLevel() {
        val nextLevel = getNextLevel.execute()
        if (nextLevel == null) {
            updateState { copy(lastLevelReached = true, currentLevel = null) }
            return
        }
        updateState {
            copy(currentLevel = nextLevel, lastLevelReached = false)
        }
    }

    fun reset() {
        resetLevels.execute()
        updateLevel()
    }
}