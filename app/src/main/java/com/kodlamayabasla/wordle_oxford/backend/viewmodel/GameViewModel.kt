package com.kodlamayabasla.wordle_oxford.backend.viewmodel

import android.util.Log
import com.kodlamayabasla.wordle_oxford.backend.models.Game
import com.kodlamayabasla.wordle_oxford.backend.models.Guess
import com.kodlamayabasla.wordle_oxford.backend.models.Word
import com.kodlamayabasla.wordle_oxford.backend.models.WordStatus
import com.kodlamayabasla.wordle_oxford.backend.usecase.GetWordStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val getWordStatus: GetWordStatus,
) : BaseViewModel<GameViewModel.State>(State()) {
    data class State(
        val game: Game = Game(),
        val currentlyEnteringWord: String? = null,
        val doesNotExist: Boolean = false,
    )
    private val originalWord = Word("")

    fun characterEntered(character: Char) {
        if (wordIsEnteredCompletely()) return
        val character = character.uppercaseChar()
        updateState {
            copy(currentlyEnteringWord = (currentlyEnteringWord ?: "") + character)
        }
    }

    private fun wordIsEnteredCompletely() =
        currentState().currentlyEnteringWord?.length == currentState().game.wordLength

    fun backspacePressed() {
        updateState {
            val newWord = when {
                currentlyEnteringWord == null -> null
                currentlyEnteringWord.length == 1 -> null
                else -> currentlyEnteringWord.dropLast(1)
            }
            copy(currentlyEnteringWord = newWord)
        }
    }

    fun submit() {
        if (!wordIsEnteredCompletely()) return
        val word = Word(currentState().currentlyEnteringWord!!)
        val status = getWordStatus.execute(word,
            currentState().game.originalWord)

        updateState {
            val newGuesses = if (status != WordStatus.NotExists) game.guesses + Guess(
                word, status
            ) else game.guesses
            copy(
                game = game.copy(guesses = newGuesses),
                currentlyEnteringWord = if (status != WordStatus.NotExists) null else currentlyEnteringWord,
                doesNotExist = if (status == WordStatus.NotExists) true else doesNotExist)
        }
    }

    fun shownNotExists() {
        updateState { copy(doesNotExist = false) }
    }

    fun shownLost() {
        updateState {
            copy(game = game.copy(guesses = listOf()),
                currentlyEnteringWord = null,
                doesNotExist = false)
        }
    }
    fun startNewGame(newWord : Word): Boolean {
        if(originalWord.word == newWord.word) return false
        originalWord.word = newWord.word
        updateState {
            copy(game = game.copy(originalWord = newWord,guesses = listOf()),
                currentlyEnteringWord = null,
                doesNotExist = false)
        }
        return true
    }
}