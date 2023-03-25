package com.kodlamayabasla.wordle_oxford.ui

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kodlamayabasla.wordle_oxford.backend.viewmodel.SettingsViewModel
import com.kodlamayabasla.wordle_oxford.ui.theme.correctBackground
import com.kodlamayabasla.wordle_oxford.ui.theme.incorrectBackground
import com.kodlamayabasla.wordle_oxford.ui.theme.onIncorrectBackground

@Composable
fun StatisticsScreen(
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
                    .padding(12.dp)
                    .verticalScroll(rememberScrollState())
                ) {
                    StatisticsTitleArea(onExpanded)
                    Spacer(modifier = Modifier.height(16.dp))
                    StatBar()
                    Spacer(modifier = Modifier.height(16.dp))
                    GuessDistributionTitleArea()
                    Spacer(modifier = Modifier.height(16.dp))
                    GuessDistributionArea()
                    Spacer(modifier = Modifier.height(48.dp))
                }
            }
        }
    }
}

@Composable
private fun StatisticsTitleArea(onExpanded: () -> Unit) {
    Box(Modifier.fillMaxWidth()){
        StatisticsTitle(modifier = Modifier.align(Center))
        IconButton(modifier = Modifier.align(TopEnd),
            onClick = { onExpanded() }) {
            Icon(Icons.Outlined.Close,"Close",)
        }
    }
}
@Composable
private fun StatisticsTitle(modifier: Modifier) {
    Text(text = "STATISTICS",
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier,
        fontWeight = FontWeight.Black
    )
}

@Composable
private fun StatBar() {
    Row(modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween) {
        Stats("1", "Played")
        Stats("100", "Win %")
        Stats("1", "Current Streak")
        Stats("1", "Max Streak")
    }
}
@Composable
private fun RowScope.Stats(topText : String, bottomText : String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        horizontalAlignment = CenterHorizontally) {
        Text(text = topText, style = MaterialTheme.typography.headlineLarge)
        Text(text = bottomText, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
private fun GuessDistributionTitleArea() {
    Row(Modifier.fillMaxWidth()){
        GuessDistributionTitle()
    }
}
@Composable
private fun GuessDistributionTitle(modifier: Modifier = Modifier) {
    Text(text = "GUESS DISTRIBUTION",
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier,
        fontWeight = FontWeight.Black
    )
}

@Composable
private fun GuessDistributionArea() {
    Column(modifier = Modifier.fillMaxWidth(0.8f),
        verticalArrangement = Arrangement.spacedBy(4.dp)) {
        GuessDistributionRow(5,1,0.3f)
        GuessDistributionRow(0,2)
        GuessDistributionRow(3,3,0.4f)
        GuessDistributionRow(10,4,1f)
        GuessDistributionRow(100,5,0.8f)
        GuessDistributionRow(1000,6,1f,MaterialTheme.colorScheme.correctBackground)
    }
}

@Composable
private fun GuessDistributionRow(gameCount : Int = 0, order : Int = 1 ,fraction : Float = 0f, color: Color = MaterialTheme.colorScheme.incorrectBackground) {
    Row(modifier = Modifier
        .fillMaxWidth()) {
        Text(text = order.toString(),
            modifier = Modifier.padding(end = 8.dp),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold)
        Row(modifier = Modifier
            .widthIn(min = 24.dp)
            .fillMaxWidth(fraction)
            .background(color = color),
            horizontalArrangement = Arrangement.End) {
            Text(text = gameCount.toString(),
                color = MaterialTheme.colorScheme.onIncorrectBackground,
                modifier = Modifier.padding(horizontal = 8.dp),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold)
        }
    }
}

@Preview
@Composable
fun StatsPreview() {
    Row {
        Stats("1", "Played")
    }
}

@Preview
@Composable
fun StatBarPreview() {
    StatBar()
}

@Preview
@Composable
fun GuessDistributionRowPreview() {
    GuessDistributionRow()
}

@Preview
@Composable
fun GuessDistributionAreaPreview() {
    GuessDistributionArea()
}