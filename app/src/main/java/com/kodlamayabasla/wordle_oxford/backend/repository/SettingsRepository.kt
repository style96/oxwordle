package com.kodlamayabasla.wordle_oxford.backend.repository

interface SettingsRepository {
    suspend fun setHardMode(enable : Boolean)
    suspend fun setDarkTheme(enable : Boolean)
    suspend fun setHightContrast(enable : Boolean)
}