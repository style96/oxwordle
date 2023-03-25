package com.kodlamayabasla.wordle_oxford.backend.repository

import android.util.Log
import androidx.datastore.core.DataStore
import com.kodlamayabasla.wordle_oxford.WordleOxfordGame
import com.kodlamayabasla.wordle_oxford.backend.models.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import java.io.IOException
import javax.inject.Inject


class GameRepositoryImpl @Inject constructor(
    private val gameStore : DataStore<WordleOxfordGame>
) : GameRepository {
    private val TAG: String = "GameRepositoryImpl"

    val userPreferencesFlow: Flow<WordleOxfordGame> = gameStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e(TAG, "Error reading sort order preferences.", exception)
                emit(WordleOxfordGame.getDefaultInstance())
            } else {
                throw exception
            }
        }
    override suspend fun saveGuesses(words : String) {
        Log.d(TAG,"words : "+ words)
        gameStore.updateData {
            it.toBuilder().setGuesses(words).build()
        }
    }
    suspend fun fetchInitialGame() = gameStore.data.first()
}