package com.kodlamayabasla.wordle_oxford.ui

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kodlamayabasla.wordle_oxford.backend.models.EqualityStatus
import com.kodlamayabasla.wordle_oxford.backend.viewmodel.SettingsViewModel

@Composable
fun HelpScreen(
    statisticsViewModel: SettingsViewModel,
    expanded : Boolean,
    onExpanded : () -> Unit) {
    val state by statisticsViewModel.state().collectAsStateWithLifecycle()
    if (expanded){
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable { onExpanded() }
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
            .focusable(true),
            contentAlignment = BottomCenter
        ){
            AnimatedVisibility(expanded,
                enter = slideInVertically(
                    initialOffsetY = {
                        it / 2
                    },
                )+ fadeIn(
                    // Fade in with the initial alpha of 0.3f.
                    initialAlpha = 0.3f
                ),
                exit = slideOutVertically(
                    targetOffsetY = {
                        it / 2
                    },
                )+ fadeOut()
            ){
                Column(modifier = Modifier
                    .shadow(elevation = 16.dp, shape = RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .clickable(enabled = false) {}
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
                ) {
                    HelpTitleArea(onExpanded)
                    Spacer(modifier = Modifier.height(16.dp))
                    DescriptionArea()
                    Spacer(modifier = Modifier.height(16.dp))
                    ExampleArea()
                    Divider(modifier = Modifier.padding(top = 20.dp))
                }
            }
        }
    }
}

@Composable
private fun HelpTitleArea(onExpanded: () -> Unit) {
    Box(Modifier.fillMaxWidth()){
        HelpTitle(modifier = Modifier.align(Center))
        IconButton(modifier = Modifier.align(TopEnd),
            onClick = { onExpanded() }) {
            Icon(Icons.Outlined.Close,"Close",)
        }
    }
}
@Composable
private fun HelpTitle(modifier: Modifier) {
    Text(text = "How To Play",
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier,
        fontWeight = FontWeight.Black,
    )
}

@Composable
private fun DescriptionArea() {
    Column() {
        Row() {
            Text(text = "Guess the Wordle in 6 tries.",
            style = MaterialTheme.typography.titleMedium,
            fontSize = 16.sp)
        }
        Row() {
            val bullet = "\u2022"
            val messages = listOf(
                "Each guess must be a valid 5-letter word.",
                "The color of the tiles will change to show how close your guess was to the word."
            )
            val paragraphStyle = ParagraphStyle(textIndent = TextIndent(restLine = 16.sp))
            Text(
                buildAnnotatedString {
                    messages.forEach {
                        withStyle(style = paragraphStyle) {
                            append(bullet)
                            append("\t\t")
                            append(it)
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun ExampleArea() {
    Column() {
        Row {
            Text(text = "Examples",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Black)
        }
        CorrectExample()
        Spacer(modifier = Modifier.height(12.dp))
        WrongPositionExample()
        Spacer(modifier = Modifier.height(12.dp))
        InCorrectExample()
    }    
}

@Composable
fun CorrectExample() {
    Column() {
        Row(
            Modifier
                .height(50.dp)
                .padding(top = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 6.dp, alignment = Alignment.Start),
            verticalAlignment = Alignment.CenterVertically) {

            WordCharacterBox(character = 'W', status = EqualityStatus.Correct)
            WordCharacterBox(character = 'E', status = null)
            WordCharacterBox(character = 'A', status = null)
            WordCharacterBox(character = 'R', status = null)
            WordCharacterBox(character = 'Y', status = null)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row() {
            Text(text = "W",
            fontWeight = FontWeight.Bold)
            Text(text = " is in the word and in the correct spot.")
        }
    }
}

@Composable
fun WrongPositionExample() {
    Column() {
        Row(
            Modifier
                .height(50.dp)
                .padding(top = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 6.dp, alignment = Alignment.Start),
            verticalAlignment = Alignment.CenterVertically) {
            WordCharacterBox(character = 'P', status = null)
            WordCharacterBox(character = 'I', status = EqualityStatus.WrongPosition)
            WordCharacterBox(character = 'L', status = null)
            WordCharacterBox(character = 'L', status = null)
            WordCharacterBox(character = 'S', status = null)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row() {
            Text(text = "I",
                fontWeight = FontWeight.Bold)
            Text(text = " is in the word but in the wrong spot.")
        }
    }
}

@Composable
fun InCorrectExample() {
    Column() {
        Row(
            Modifier
                .height(50.dp)
                .padding(top = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 6.dp, alignment = Alignment.Start),
            verticalAlignment = Alignment.CenterVertically) {

            WordCharacterBox(character = 'V', status = null)
            WordCharacterBox(character = 'A', status = null)
            WordCharacterBox(character = 'G', status = null)
            WordCharacterBox(character = 'U', status = EqualityStatus.Incorrect)
            WordCharacterBox(character = 'E', status = null)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row() {
            Text(text = "U",
                fontWeight = FontWeight.Bold)
            Text(text = " is not in the word in any spot.")
        }
    }
}

@Preview
@Composable
fun DescriptionAreaPreview() {
    DescriptionArea()
}

@Preview
@Composable
fun ExampleAreaPreview() {
    ExampleArea()
}