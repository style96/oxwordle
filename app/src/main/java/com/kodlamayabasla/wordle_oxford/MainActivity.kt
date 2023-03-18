package com.kodlamayabasla.wordle_oxford

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kodlamayabasla.wordle_oxford.backend.models.Game
import com.kodlamayabasla.wordle_oxford.backend.repository.AssetFileWordRepository
import com.kodlamayabasla.wordle_oxford.backend.repository.LocalStorageLevelRepository
import com.kodlamayabasla.wordle_oxford.backend.usecase.GetNextLevel
import com.kodlamayabasla.wordle_oxford.backend.usecase.GetWordStatus
import com.kodlamayabasla.wordle_oxford.backend.usecase.ResetLevels
import com.kodlamayabasla.wordle_oxford.backend.viewmodel.GameViewModel
import com.kodlamayabasla.wordle_oxford.backend.viewmodel.GameViewModelFactory
import com.kodlamayabasla.wordle_oxford.backend.viewmodel.LevelsViewModel
import com.kodlamayabasla.wordle_oxford.backend.viewmodel.LevelsViewModelFactory
import com.kodlamayabasla.wordle_oxford.ui.GameHeader
import com.kodlamayabasla.wordle_oxford.ui.WordScreen
import com.kodlamayabasla.wordle_oxford.ui.theme.OxWordleTheme

class MainActivity : ComponentActivity() {
    val tag = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OxWordleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // simple dependency injection
                    val assetWordRepository = remember {
                        AssetFileWordRepository(assets)
                    }
                    val getWordStatus = remember {
                        GetWordStatus(assetWordRepository)
                    }

                    val sharedPreferences: SharedPreferences = remember {
                        getSharedPreferences("default", MODE_PRIVATE)
                    }
                    val levelRepository = remember {
                        LocalStorageLevelRepository(sharedPreferences)
                    }

                    val getNextLevel = remember {
                        GetNextLevel(assetWordRepository, levelRepository)
                    }
                    val resetLevels = remember {
                        ResetLevels(levelRepository)
                    }

                    val levelViewModel:LevelsViewModel = viewModel(
                        factory = LevelsViewModelFactory(levelRepository, getNextLevel, resetLevels)
                    )
                    val level = levelViewModel.state().collectAsState().value.currentLevel
                    Log.d("deneme","deneme")

                    if (level != null) {
                        val word = remember(level.word){
                            level.word
                        }
                        val initialGame = remember(word) {
                            Game(word, listOf(), 5)
                        }
                        val gameViewModel: GameViewModel = viewModel(
                            factory = GameViewModelFactory(initialGame, getWordStatus))
                        remember(level.word) {
                            gameViewModel.startNewGame(level.word)
                        }
                        WordScreen(level, gameViewModel) {
                            levelViewModel.levelPassed()
                        }
                    }
                    else {
                        Box(contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                var expanded by remember { mutableStateOf(false) }
                                GameHeader(onExpanded = {expanded = !expanded}) {

                                }
                                Text(text = "You have mastered the game (1024 levels)!",
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(top = 32.dp))

                                Text(
                                    text = "Want to reset to the first level?",
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(top = 32.dp),
                                )
                                Button(
                                    onClick = {
                                        levelViewModel.reset()
                                    },
                                    modifier = Modifier.padding(top = 16.dp),
                                ) {
                                    Text(text = "Reset")
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}