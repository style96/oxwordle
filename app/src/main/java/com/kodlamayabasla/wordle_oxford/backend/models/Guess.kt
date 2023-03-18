package com.kodlamayabasla.wordle_oxford.backend.models

data class Guess(
    val word: Word,
    val wordStatus: WordStatus
)