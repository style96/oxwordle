package com.kodlamayabasla.wordle_oxford.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kodlamayabasla.wordle_oxford.backend.models.Level
import com.kodlamayabasla.wordle_oxford.backend.viewmodel.GameViewModel
import com.kodlamayabasla.wordle_oxford.backend.viewmodel.SettingsViewModel

@Composable
internal fun WordScreen(
    level: Level,
    viewModel: GameViewModel,
    settingsViewModel: SettingsViewModel = viewModel(),
    levelCompleted: () -> Unit,
) {
    val state by viewModel.state().collectAsState()
    GameScreen(
        level,
        state,
        settingsViewModel,
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
    settingsViewModel: SettingsViewModel,
    onKey: (char: Char) -> Unit,
    onBackspace: () -> Unit,
    onSubmit: () -> Unit,
    shownError: () -> Unit,
    shownWon: () -> Unit,
    shownLost: () -> Unit,
) {
    var settings by remember { mutableStateOf(false) }
    var statistics by remember { mutableStateOf(false) }
    var help by remember { mutableStateOf(false) }
    Box(
        Modifier
            .fillMaxSize(),
        contentAlignment = TopCenter
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 16.dp)
                .widthIn(max = 500.dp),
            verticalArrangement = Arrangement.SpaceBetween
                ) {
            GameHeader(
                level = level,
                onSettings = {settings=!settings},
                onStatistics = {statistics = !statistics},
                onHelp = {help = !help})
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
        SettingsScreen(settingsViewModel,settings, onExpanded = {settings = !settings})
        StatisticsScreen(settingsViewModel,statistics, onExpanded = {statistics = !statistics})
        ErrorScreen(state, shownError)
        WonScreen(state, shownWon)
        GameOverScreen(state, shownLost)
    }
}

