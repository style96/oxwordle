package com.kodlamayabasla.wordle_oxford.ui

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodlamayabasla.wordle_oxford.R
import com.kodlamayabasla.wordle_oxford.backend.models.Level

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun ColumnScope.GameHeader(level: Level,
                                    modifier: Modifier = Modifier,
                                    onSettings : () -> Unit,
                                    onStatistics : () -> Unit,
                                    onHelp : () -> Unit,) {
    var revealing by remember(level) { mutableStateOf(false) }
    GameHeader(modifier,onSettings,onStatistics,onHelp) {
        LevelHeaderContent(level, revealing) {
            revealing = it
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun ColumnScope.GameHeader(
    modifier: Modifier = Modifier,
    onSettings : () -> Unit,
    onStatistics : () -> Unit,
    onHelp : () -> Unit,
    content: @Composable () -> Unit,
) {
    Column(modifier
        .align(Alignment.CenterHorizontally)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            GameHeaderLeft(onHelp = onHelp)
            GameHeaderTitle()
            GameHeaderRight(onSettings =  onSettings, onStatistics = onStatistics )
        }
        content()
    }

}

@Composable
fun RowScope.GameHeaderLeft(onHelp : () -> Unit){
    Row(modifier = Modifier.weight(0.5f)){
        IconButton(modifier = Modifier.weight(1f),onClick = onHelp) {
            Icon(painter = painterResource(R.drawable.baseline_help_outline_24),"info",Modifier.weight(1f))
        }
    }
}
@Composable
fun RowScope.GameHeaderTitle(){
    Row(modifier = Modifier.weight(1f),
    horizontalArrangement = Arrangement.Center) {
        Text(text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.Serif,
        )
    }
}
@Composable
fun RowScope.GameHeaderRight(onSettings : () -> Unit, onStatistics : () -> Unit){
    Row(modifier = Modifier.weight(0.5f)) {
        IconButton(modifier = Modifier.weight(1f), onClick = onStatistics) {
            Icon(painter = painterResource(R.drawable.baseline_leaderboard_24), "statistic",Modifier.weight(1f))
        }
        IconButton(modifier = Modifier.weight(1f),onClick = onSettings ) {
            Icon(Icons.Default.Settings,"settings",Modifier.weight(1f))
        }
    }

}

@Preview()
@Composable
fun GameHeaderPreview(){
    val modifier = Modifier
    var settings by remember { mutableStateOf(false) }
    var statistics by remember { mutableStateOf(false) }
    var help by remember { mutableStateOf(false) }
    Column(modifier) {
        Row(modifier = Modifier.fillMaxWidth()) {
            GameHeaderLeft {help = !help}
            GameHeaderTitle()
            GameHeaderRight(onSettings = {settings=!settings}, onStatistics = {statistics = !statistics})
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TopAppBarPreview() {
    TopAppBar(
        title = {
        Text(
        "OxWordle!"
        )},
        navigationIcon = {
            IconButton(modifier = Modifier, onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Info,"info",)
            }
        },
        actions = {
            IconButton(modifier = Modifier,onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Person,"statistic",)
            }
            IconButton(modifier = Modifier,onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Settings,"settings",)
            }
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ColumnScope.LevelHeaderContent(
    level: Level,
    revealing: Boolean,
    onRevealChanged: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.Companion
            .align(
                Alignment.CenterHorizontally
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Level ${level.number}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.Serif,
        )
        Box(modifier = Modifier.padding(start = 16.dp)) {
            if (!revealing) {
                Text(text = "(reveal)",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.clickable {
                        onRevealChanged(true)
                    })
            } else {
                Text(text = level.word.word, style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,

                    modifier = Modifier.clickable {
                        onRevealChanged(false)
                    })
            }
        }

    }
}