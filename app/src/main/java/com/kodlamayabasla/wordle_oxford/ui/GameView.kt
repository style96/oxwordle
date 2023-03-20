package com.kodlamayabasla.wordle_oxford.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kodlamayabasla.wordle_oxford.backend.models.EqualityStatus
import com.kodlamayabasla.wordle_oxford.backend.models.WordStatus
import com.kodlamayabasla.wordle_oxford.backend.viewmodel.GameViewModel
import com.kodlamayabasla.wordle_oxford.ui.theme.*

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
internal fun GameGrid(
    state: GameViewModel.State,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            repeat(6) { row ->
                Row(
                    Modifier
                        .weight(1f,false)
                        .padding(top = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(space = 6.dp, alignment = Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically) {
                    repeat(5) { column ->
                        val character: Char?
                        val status: EqualityStatus?
                        if (row < state.game.guesses.size) {
                            val guess = state.game.guesses[row]
                            character = guess.word.word[column]
                            status = when (guess.wordStatus) {
                                WordStatus.Correct -> EqualityStatus.Correct
                                is WordStatus.Incorrect -> guess.wordStatus.equalityStatuses[column]
                                WordStatus.NotExists -> EqualityStatus.Incorrect
                                else -> EqualityStatus.Incorrect
                            }
                        } else {
                            character =
                                if (row == state.game.guesses.size) state.currentlyEnteringWord?.getOrNull(
                                    column) else null
                            status = null
                        }


                        WordCharacterBox(character = character,
                            status = status,
                            modifier = Modifier.weight(1f))
                    }

                }
            }
        }
    }
}

@Composable
internal fun WordCharacterBox(
    character: Char?,
    status: EqualityStatus?,
    modifier: Modifier = Modifier,
) {

    val color = when (status) {
        EqualityStatus.WrongPosition -> MaterialTheme.colorScheme.wrongPositionBackground
        EqualityStatus.Correct -> MaterialTheme.colorScheme.correctBackground
        EqualityStatus.Incorrect -> MaterialTheme.colorScheme.incorrectBackground
        null -> MaterialTheme.colorScheme.enteringBackground
    }

    val textColor = when (status) {
        null -> MaterialTheme.colorScheme.onBackground
        EqualityStatus.WrongPosition -> MaterialTheme.colorScheme.onWrongPositionBackground
        EqualityStatus.Correct -> MaterialTheme.colorScheme.onCorrectBackground
        EqualityStatus.Incorrect -> MaterialTheme.colorScheme.onIncorrectBackground
    }
    val borderModifier = if (status == null) Modifier.border(
        1.dp,
        MaterialTheme.colorScheme.border) else Modifier
    BasicCharacterBox(borderModifier, color, character, textColor, modifier)
}

@Composable
fun EmptyCharacterBox(
    modifier: Modifier = Modifier,
) {
    BasicCharacterBox(modifier = modifier, borderModifier = Modifier.border(1.dp,
        MaterialTheme.colorScheme.incorrectBackground),
        color = MaterialTheme.colorScheme.onBackground,
        character = null,
        textColor = Color.Transparent)
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
internal fun BasicCharacterBox(
    borderModifier: Modifier,
    color: Color,
    character: Char?,
    textColor: Color,
    modifier: Modifier = Modifier,
) {
    var lastChar by remember { mutableStateOf<Char?>(null) }
    if (character != null) {
        lastChar = character
    }
    Box(
        modifier
            .aspectRatio(1f,true)
            .clip(RoundedCornerShape(2.dp))
            .then(borderModifier)
            .background(animateColorAsState(targetValue = color).value),
        contentAlignment = Alignment.Center) {
        AnimatedVisibility(character != null) {
            Text(lastChar?.uppercase() ?: "",
                color = animateColorAsState(targetValue = textColor).value,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                ))
        }
    }
}

@Preview
@Composable
internal fun CharacterBoxPreview() {
    Row {
        WordCharacterBox(character = 'A', status = null)
        WordCharacterBox(character = 'D', status = EqualityStatus.Incorrect)
        WordCharacterBox(character = 'I', status = EqualityStatus.WrongPosition)
        WordCharacterBox(character = 'B', status = EqualityStatus.Correct)
    }
}