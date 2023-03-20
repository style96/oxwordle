package com.kodlamayabasla.wordle_oxford.ui

import android.content.Context
import android.os.Vibrator
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kodlamayabasla.wordle_oxford.backend.models.EqualityStatus
import com.kodlamayabasla.wordle_oxford.backend.models.KeyboardKeys
import com.kodlamayabasla.wordle_oxford.backend.viewmodel.GameViewModel
import com.kodlamayabasla.wordle_oxford.ui.theme.*

@Composable
internal fun GameKeyboard(
    state: GameViewModel.State,
    modifier: Modifier = Modifier,
    onKey: (char: Char) -> Unit,
    onBackspace: () -> Unit,
    onSubmit: () -> Unit,
) {
    BoxWithConstraints(modifier) {
        Column {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()) {

                repeat(10) {
                    val key = state.game.availableKeyboard.keys[it]
                    KeyboardKey(key, onKey, Modifier.weight(1f))
                }
            }
            Spacer(Modifier.size(6.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
            )
            {
                Spacer(modifier = Modifier.weight(0.5f))
                repeat(9) {
                    val key = state.game.availableKeyboard.keys[10 + it]
                    KeyboardKey(key, onKey, Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.weight(0.5f))
            }
            Spacer(Modifier.size(6.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier) {

                Box(
                    modifier
                        .height(50.dp)
                        .weight(1.5f)
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.keyboard)
                        .clickable(onClick = onSubmit), Alignment.Center) {
                    Text(text = "ENTER",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,

                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .clip(RoundedCornerShape(4.dp)))
                }

                repeat(7) {
                    val key = state.game.availableKeyboard.keys[19 + it]
                    KeyboardKey(key, onKey, Modifier.weight(1f))
                }

                KeyboardKey(text = "⌫",
                    modifier = Modifier.weight(1.5f),
                    onClick = onBackspace)
            }
        }
    }
}

@Composable
private fun KeyboardKey(
    key: KeyboardKeys.Key,
    onKey: (char: Char) -> Unit,
    modifier: Modifier = Modifier,
) {
    KeyboardKey(key.button.toString().uppercase(), modifier = modifier, key.equalityStatus) {
        onKey(key.button)
    }
}

@Composable
private fun KeyboardKey(
    text: String,
    modifier: Modifier = Modifier,
    status: EqualityStatus? = null,
    onClick: () -> Unit,
) {
    val color by animateColorAsState(targetValue = when (status) {
        EqualityStatus.Incorrect -> MaterialTheme.colorScheme.incorrectBackground
        EqualityStatus.WrongPosition -> MaterialTheme.colorScheme.wrongPositionBackground
        EqualityStatus.Correct -> MaterialTheme.colorScheme.correctBackground
        else -> MaterialTheme.colorScheme.keyboard
    })
    val testColor by animateColorAsState(targetValue = when (status) {
        EqualityStatus.WrongPosition -> MaterialTheme.colorScheme.onWrongPositionBackground
        EqualityStatus.Correct -> MaterialTheme.colorScheme.onCorrectBackground
        EqualityStatus.Incorrect -> MaterialTheme.colorScheme.onIncorrectBackground
        else -> MaterialTheme.colorScheme.onKeyboard
    })
    val context = LocalContext.current
    Box(modifier
        .height(50.dp)
        .clip(RoundedCornerShape(4.dp))
        .background(color)
        .clickable(onClick = {
            //context.vibrate()
            onClick()
        }), Alignment.Center) {
        Text(
            modifier = Modifier,
            text = text,
            color = testColor,
            fontSize = 24.sp
        )
    }
}

/*
private fun Context.vibrate() {
    val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    vibrator.vibrate(10)
}

 */