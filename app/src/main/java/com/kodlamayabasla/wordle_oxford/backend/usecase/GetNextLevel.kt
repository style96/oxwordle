package com.kodlamayabasla.wordle_oxford.backend.usecase

import com.kodlamayabasla.wordle_oxford.backend.models.Level
import com.kodlamayabasla.wordle_oxford.backend.repository.LevelRepository
import com.kodlamayabasla.wordle_oxford.backend.repository.WordRepository
import javax.inject.Inject

class GetNextLevel @Inject constructor(
    private val wordRepository: WordRepository,
    private val levelRepository: LevelRepository,
) {
    fun execute(): Level? {
        val currentLevelNumber = levelRepository.getCurrentLevelNumber()
        if (currentLevelNumber >= wordRepository.lastLevel + 1) return null
        return wordRepository.getWordForLevel(currentLevelNumber).let { word ->
            Level(currentLevelNumber, word)
        }
    }
}