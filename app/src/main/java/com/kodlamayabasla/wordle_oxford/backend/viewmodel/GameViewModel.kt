package com.kodlamayabasla.wordle_oxford.backend.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.kodlamayabasla.wordle_oxford.backend.models.Game
import com.kodlamayabasla.wordle_oxford.backend.models.Guess
import com.kodlamayabasla.wordle_oxford.backend.models.Word
import com.kodlamayabasla.wordle_oxford.backend.models.WordStatus
import com.kodlamayabasla.wordle_oxford.backend.repository.GameRepositoryImpl
import com.kodlamayabasla.wordle_oxford.backend.usecase.GetNextLevel
import com.kodlamayabasla.wordle_oxford.backend.usecase.GetWordStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val getWordStatus: GetWordStatus,
    private val gameRepository: GameRepositoryImpl,
    private val getNextLevel: GetNextLevel
) : BaseViewModel<GameViewModel.State>(State()) {
    data class State(
        val game: Game = Game(),
        val currentlyEnteringWord: String? = null,
        val doesNotExist: Boolean = false,
    )
    private val originalWord = Word("")
    init {
        updateLevel()
        initFlow()
    }


    fun initFlow() {
        Log.d("GameViewModel","initflow")
        viewModelScope.launch{
            gameRepository.fetchInitialGame().let {
                val words = it.guesses
                if(words == ""){
                    return@launch
                }
                Log.d("GameViewModel","words : " + words)
                val wordList = words.split(",").map {
                    Word(it)
                }
                Log.d("GameViewModel","wordList : " + wordList)

                updateState {
                    val guessList: List<Guess> = wordList.map {word ->
                        val status = getWordStatus.execute(word, game.originalWord)
                        Guess(word,status)
                    }
                    copy(
                        game = game.copy(guesses = guessList)
                    )
                }
            }
        }
    }

    private fun updateLevel() {
        val nextLevel = getNextLevel.execute() ?: return
        originalWord.word = nextLevel.word.word
        updateState {
            copy(game.copy(originalWord = nextLevel.word))
        }
    }

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
            saveToDB(newGuesses)
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
        saveToDB(listOf())
        updateState {
            copy(game = game.copy(guesses = listOf()),
                currentlyEnteringWord = null,
                doesNotExist = false)
        }
    }
    fun startNewGame(newWord : Word): Boolean {
        if(originalWord.word == newWord.word) return false
        originalWord.word = newWord.word
        Log.d("GameViewModel","StartNewGame")
        saveToDB(listOf())
        updateState {
            copy(game = game.copy(originalWord = newWord,guesses = listOf()),
                currentlyEnteringWord = null,
                doesNotExist = false)
        }
        return true
    }

    fun startNewGame2() {
        updateLevel()
        Log.d("GameViewModel","StartNewGame2")
        saveToDB(listOf())
        updateState {
            copy(game = game.copy(guesses = listOf()),
                currentlyEnteringWord = null,
                doesNotExist = false)
        }
    }

    private fun saveToDB(newGuesses : List<Guess>){
        viewModelScope.launch {
            val wordList = newGuesses.map {
                it.word.word
            }
            val words = wordList.joinToString(separator = ",")
            Log.d("GameViewModel","saveToDB : " + words)
            gameRepository.saveGuesses(words)
        }
    }
}