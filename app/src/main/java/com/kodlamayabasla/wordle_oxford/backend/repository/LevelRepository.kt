package com.kodlamayabasla.wordle_oxford.backend.repository

import com.kodlamayabasla.wordle_oxford.backend.models.Level

interface LevelRepository {
    fun getCurrentLevelNumber(): Long
    fun levelPassed(level: Level)
    fun reset()
}