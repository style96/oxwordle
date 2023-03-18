package com.kodlamayabasla.wordle_oxford.backend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kodlamayabasla.wordle_oxford.backend.models.Game
import com.kodlamayabasla.wordle_oxford.backend.repository.LevelRepository
import com.kodlamayabasla.wordle_oxford.backend.usecase.GetNextLevel
import com.kodlamayabasla.wordle_oxford.backend.usecase.GetWordStatus
import com.kodlamayabasla.wordle_oxford.backend.usecase.ResetLevels

class GameViewModelFactory(private val initialGame: Game,
                           private val getWordStatus: GetWordStatus)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(GameViewModel::class.java))
            return GameViewModel(initialGame,getWordStatus) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}