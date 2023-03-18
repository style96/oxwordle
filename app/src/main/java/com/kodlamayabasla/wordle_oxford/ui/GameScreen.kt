package com.kodlamayabasla.wordle_oxford.ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kodlamayabasla.wordle_oxford.backend.models.Game
import com.kodlamayabasla.wordle_oxford.backend.models.Level
import com.kodlamayabasla.wordle_oxford.backend.usecase.GetWordStatus
import com.kodlamayabasla.wordle_oxford.backend.viewmodel.GameViewModel
import com.kodlamayabasla.wordle_oxford.backend.viewmodel.GameViewModelFactory

@Composable
internal fun WordScreen(
    level: Level,
    viewModel: GameViewModel,
    levelCompleted: () -> Unit,
) {
    Log.d("deneme","deneme")
    val state by viewModel.state().collectAsState()
    GameScreen(
        level,
        state,
        onKey = {
            viewModel.characterEntered(it)
        },
        onBackspace = {
            viewModel.backspacePressed()
        },
        onSubmit = {
            viewModel.submit()
        },
        shownError = {
            viewModel.shownNotExists()
        },
        shownWon = levelCompleted,

        shownLost = {
            viewModel.shownLost()
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameScreen(
    level: Level,
    state: GameViewModel.State,
    onKey: (char: Char) -> Unit,
    onBackspace: () -> Unit,
    onSubmit: () -> Unit,
    shownError: () -> Unit,
    shownWon: () -> Unit,
    shownLost: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        Modifier
            .fillMaxSize(),
        contentAlignment = Center
    ) {
        Column(
            Modifier
                .padding(horizontal = 8.dp, vertical = 16.dp)
                .widthIn(max = 500.dp)
                ) {
            GameHeader(level,onExpanded = {expanded = !expanded})
            GameGrid(
                state,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .weight(1f)
                    .fillMaxWidth(0.8f)
                    .align(CenterHorizontally),
                )
            Spacer(modifier = Modifier.size(16.dp))
            GameKeyboard(
                state,
                modifier = Modifier.align(CenterHorizontally),
                onKey = onKey,
                onBackspace = onBackspace,
                onSubmit = onSubmit,
            )
        }
        SettingsScreen(expanded, onExpanded = {expanded = !expanded})
        ErrorScreen(state, shownError)
        WonScreen(state, shownWon)
        GameOverScreen(state, shownLost)
    }
}

