package com.kodlamayabasla.wordle_oxford.backend.di

import com.kodlamayabasla.wordle_oxford.backend.repository.AssetFileWordRepository
import com.kodlamayabasla.wordle_oxford.backend.repository.LevelRepository
import com.kodlamayabasla.wordle_oxford.backend.repository.LocalStorageLevelRepository
import com.kodlamayabasla.wordle_oxford.backend.repository.WordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLevelRepository(
        localStorageLevelRepository: LocalStorageLevelRepository
    ): LevelRepository

    @Binds
    @Singleton
    abstract fun bindWordRepository(
        assetFileWordRepository: AssetFileWordRepository
    ): WordRepository
}