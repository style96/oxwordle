package com.kodlamayabasla.wordle_oxford.backend.repository

import android.util.Log
import androidx.datastore.core.DataStore
import com.kodlamayabasla.wordle_oxford.UserPrefs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import java.io.IOException
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val userPreferencesStore: DataStore<UserPrefs>
) : SettingsRepository {
    private val TAG: String = "SettingsRepositoryImpl"

    val userPreferencesFlow: Flow<UserPrefs> = userPreferencesStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e(TAG, "Error reading sort order preferences.", exception)
                emit(UserPrefs.getDefaultInstance())
            } else {
                throw exception
            }
        }

    override suspend fun setHardMode(enable: Boolean) {
        userPreferencesStore.updateData { currentPreferences ->
            currentPreferences.toBuilder().setHardMode(enable).build()
        }
    }

    override suspend fun setDarkTheme(enable: Boolean) {
        userPreferencesStore.updateData { currentPreferences ->
            currentPreferences.toBuilder().setDarkTheme(enable).build()
        }
    }

    override suspend fun setHightContrast(enable: Boolean) {
        userPreferencesStore.updateData { currentPreferences ->
            currentPreferences.toBuilder().setHighContrast(enable).build()
        }
    }
    suspend fun fetchInitialPreferences() = userPreferencesStore.data.first()
}