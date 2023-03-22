package com.kodlamayabasla.wordle_oxford.backend.usecase

import com.kodlamayabasla.wordle_oxford.backend.repository.LevelRepository
import javax.inject.Inject

class ResetLevels @Inject constructor(
    private val levelRepository: LevelRepository,
) {
    fun execute() {
        levelRepository.reset()
    }
}