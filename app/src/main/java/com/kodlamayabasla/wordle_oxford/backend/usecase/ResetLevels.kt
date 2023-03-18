package com.kodlamayabasla.wordle_oxford.backend.usecase

import com.kodlamayabasla.wordle_oxford.backend.repository.LevelRepository

class ResetLevels(
    private val levelRepository: LevelRepository,
) {
    fun execute() {
        levelRepository.reset()
    }
}