package com.kodlamayabasla.wordle_oxford.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kodlamayabasla.wordle_oxford.backend.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel,
    expanded : Boolean,
    onExpanded : () -> Unit) {
    val state by settingsViewModel.state().collectAsStateWithLifecycle()

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
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState())
                ) {
                    SettingTitleArea(onExpanded)
                    HardModeSwitchSettingItem(state.hardMode){ settingsViewModel.setHardMode(it) }
                    Divider()
                    DarkThemeSwitchSettingItem(state.darkTheme){ settingsViewModel.setDarkTheme(it)}
                    Divider()
                    HightContrastSwitchSettingItem(state.highContrast){ settingsViewModel.setHightContrast(it)}
                    Divider()
                    TwitterSettingItem()
                    Divider()
                    WebSiteSettingItem()
                    Divider()
                    PrivacyPolicyItem()
                    Divider()
                    TermsConditionsItem()
                    Divider()

                    Spacer(modifier = Modifier.height(50.dp))
                    CopyRight()
                }
            }
        }
    }
}

@Composable
private fun SettingTitleArea(onExpanded: () -> Unit) {
    Box(Modifier.fillMaxWidth()){
        SettingTitle(modifier = Modifier.align(Center))
        IconButton(modifier = Modifier.align(TopEnd),
            onClick = { onExpanded() }) {
            Icon(Icons.Outlined.Close,"Close",)
        }
    }
}
@Composable
private fun SettingTitle(modifier: Modifier) {
    Text(text = "SETTINGS",
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier,
        fontWeight = FontWeight.Black
    )
}
@Composable
private fun CopyRight() {
    Text(text = "© 2023 Kodlamaya Basla",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
}
@Composable
private fun SwitchItem(
    title: String,
    subTitle: String? = null,
    checked: Boolean = false,
    onChanged: (Boolean) -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween) {
        Column(verticalArrangement = Arrangement.Center) {
            Text(text = title,
                style = MaterialTheme.typography.titleMedium)
            if (subTitle != null) {
                Text(text = subTitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f))
            }
        }
        Switch(checked = checked, onCheckedChange = { onChanged(it) } )
    }
}

@Composable
private fun HardModeSwitchSettingItem(checked : Boolean, onChanged : (Boolean) -> Unit) {
    SwitchItem("Hard Mode", "Longer text is here for the subtitle",checked,onChanged)
}
@Composable
private fun DarkThemeSwitchSettingItem(checked : Boolean, onChanged : (Boolean) -> Unit) {
    SwitchItem("Dark Theme",null,checked,onChanged)
}
@Composable
private fun HightContrastSwitchSettingItem(checked : Boolean, onChanged : (Boolean) -> Unit) {
    SwitchItem("High Contrast Mode", "For improved color vision",checked,onChanged)
}

@Composable
private fun OpenLinkSettingItem(title: String, linkTitle: String, url : String) {
    val uriHandler = LocalUriHandler.current
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)

        ClickableText(
            text = AnnotatedString(linkTitle),
            onClick = { uriHandler.openUri(url) },
            style = TextStyle(color = MaterialTheme.colorScheme.onBackground)
        )
    }
}

@Composable
private fun WebSiteSettingItem() {
    OpenLinkSettingItem("Questions", "Kodlamaya Basla","https://kodlamayabasla.com")
}
@Composable
private fun TwitterSettingItem() {
    OpenLinkSettingItem("Community", "Twitter","https://twitter.com/sn_halil")
}

@Composable
private fun PrivacyPolicyItem() {
    OpenLinkSettingItem("Privacy Policy", "Read Privacy Policy","https://kodlamayabasla.com/privacy-policy-oxwordle/")
}
@Composable
private fun TermsConditionsItem() {
    OpenLinkSettingItem("Terms & Conditions", "Read Terms & Conditions","https://kodlamayabasla.com/terms-conditions-oxwordle/")
}

@Preview
@Composable
fun ClickableSettingItemPreview() {
    OpenLinkSettingItem("Follow Us", "Kodlamaya Basla","https://kodlamayabasla.com")
}


@Preview
@Composable
fun SwitchItemPreview() {
    val checked = remember {
        mutableStateOf(false)
    }
    Column {
        SwitchItem("Hard Mode",null,checked.value, onChanged = {checked.value = !checked.value})
        Divider()
        SwitchItem("Hard Mode",null,checked.value, onChanged = {checked.value = !checked.value})
    }
}

@Preview
@Composable
fun SwitchItemSubtitlePreview() {
    val checked = remember {
        mutableStateOf(false)
    }
    SwitchItem("Hard Mode", "Longer text is here for the subtitle" , checked.value, onChanged = {checked.value = !checked.value})
}

@Preview
@Composable
fun SettingTitlePreview() {
    SettingTitle(modifier = Modifier)
}