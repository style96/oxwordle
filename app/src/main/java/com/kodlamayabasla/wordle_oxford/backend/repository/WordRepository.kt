package com.kodlamayabasla.wordle_oxford.backend.repository

import com.kodlamayabasla.wordle_oxford.backend.models.Word

interface WordRepository {
    val lastLevel: Long
    fun find(word: Word): Boolean
    fun random(): Word
    fun getWordForLevel(currentLevelNumber: Long): Word
}

