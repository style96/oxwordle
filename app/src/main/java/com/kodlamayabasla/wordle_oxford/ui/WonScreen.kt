package com.kodlamayabasla.wordle_oxford.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kodlamayabasla.wordle_oxford.backend.viewmodel.GameViewModel
import com.kodlamayabasla.wordle_oxford.ui.theme.onCorrectBackground

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun WonScreen(
    state: GameViewModel.State,
    shownWon: () -> Unit,
) {

    if(state.game.isWon){
        AlertDialog(onDismissRequest = {shownWon()}) {
            Box(Modifier
                .clickable {
                    shownWon()
                }
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.primary),
                Alignment.Center) {
                Text(text = "LEVEL PASSED!",
                    color = MaterialTheme.colorScheme.onCorrectBackground,
                    modifier = Modifier.padding(32.dp),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Black)
            }
        }
    }










/*
    AnimatedVisibility(state.game.isWon, modifier = Modifier.fillMaxSize(),
        enter = fadeIn(), exit = fadeOut()
    ) {

        Box(Modifier
            .fillMaxSize()
            .background(Color(0x7F000000))
            .clickable {
                shownWon()
            }
            .padding(16.dp)
            .wrapContentHeight()
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colorScheme.primary),
            Alignment.Center) {
            Text(text = "LEVEL PASSED!",
                color = MaterialTheme.colorScheme.onCorrectBackground,
                modifier = Modifier.padding(32.dp),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black)
        }


    }

 */
}