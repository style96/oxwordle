package com.kodlamayabasla.wordle_oxford.backend.repository

import com.kodlamayabasla.wordle_oxford.backend.models.Word

interface GameRepository {
    suspend fun saveGuesses(wordList :String)
}