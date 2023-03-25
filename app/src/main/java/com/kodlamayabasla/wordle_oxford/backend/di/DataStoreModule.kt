package com.kodlamayabasla.wordle_oxford.backend.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.kodlamayabasla.wordle_oxford.UserPrefs
import com.kodlamayabasla.wordle_oxford.WordleOxfordGame
import com.kodlamayabasla.wordle_oxford.backend.repository.GameSerializer
import com.kodlamayabasla.wordle_oxford.backend.repository.SettingsSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val USER_PREFS_DATA_STORE_FILE_NAME = "wordle_oxford_user_prefs.pb"
private const val GAME_DATA_STORE_FILE_NAME = "wordle_oxford_game.pb"

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideProtoDataStore(@ApplicationContext appContext: Context): DataStore<UserPrefs> {
        return DataStoreFactory.create(
            serializer = SettingsSerializer,
            produceFile = { appContext.dataStoreFile(USER_PREFS_DATA_STORE_FILE_NAME) },
            )
    }

    @Singleton
    @Provides
    fun provideGameDataStore(@ApplicationContext appContext: Context): DataStore<WordleOxfordGame> {
        return DataStoreFactory.create(
            serializer = GameSerializer,
            produceFile = { appContext.dataStoreFile(GAME_DATA_STORE_FILE_NAME) },
        )
    }
}