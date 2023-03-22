package com.kodlamayabasla.wordle_oxford.backend.di

import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import androidx.activity.ComponentActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context : Context): SharedPreferences {
        return context.getSharedPreferences("default", ComponentActivity.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAssetManager(@ApplicationContext context: Context): AssetManager {
        return context.assets
    }
}