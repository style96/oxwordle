package com.kodlamayabasla.wordle_oxford.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.kodlamayabasla.wordle_oxford.ui.theme.onCorrectBackground

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun BoxScope.SettingsScreen(
    expanded : Boolean,
    onExpanded : () -> Unit) {
    /*
    if(expanded) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable { onExpanded() }
            .background(Color.Black.copy(alpha = 0.3f))
            .focusable(true)
        ){
            
            Dialog(onDismissRequest = onExpanded) {
                Column(modifier = Modifier
                    .align(BottomCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .background(MaterialTheme.colorScheme.background)
                    .clickable(enabled = false) {}
                    .focusable(true)
                ){
                    val focusRequester = remember { FocusRequester() }
                    IconButton(modifier = Modifier
                        .align(End),
                        onClick = { onExpanded() }) {
                        Icon(Icons.Outlined.Close,"Close",)
                    }
                    DropdownMenuItem(
                        modifier = Modifier
                            .focusable(true)
                            .focusRequester(focusRequester),
                        text = { Text("Settings") },
                        onClick = { /* Handle settings! */ },
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.Settings,
                                contentDescription = null
                            )
                        })
                    Divider()
                    Row() {
                        Text(text = "Hard Mode", modifier = Modifier.focusable(true))
                        Switch(checked = false, onCheckedChange = {  },modifier = Modifier.focusable(true))

                    }

                    Row() {
                        Switch(checked = false, onCheckedChange = {  })

                    }
                }
            }
            

        }
    }
*/

    if(expanded){
        AlertDialog(onDismissRequest = onExpanded,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .align(BottomCenter),
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Column(modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
            ) {
                IconButton(modifier = Modifier.align(End),
                    onClick = { onExpanded() }) {
                    Icon(Icons.Outlined.Close,"Close",)
                }
                DropdownMenuItem(
                    text = { Text("Hard Mode") },
                    onClick = { /* Handle edit! */ },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Edit,
                            contentDescription = null
                        )
                    })
                DropdownMenuItem(
                    text = { Text("Settings") },
                    onClick = { /* Handle settings! */ },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Settings,
                            contentDescription = null
                        )
                    })
                Divider()
                DropdownMenuItem(
                    text = { Text("Send Feedback") },
                    onClick = { /* Handle send feedback! */ },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Email,
                            contentDescription = null
                        )
                    },
                    trailingIcon = { Text("F11", textAlign = TextAlign.Center) })
            }

        }
    }


    /*

    DropdownMenu(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
            .padding(8.dp),
        expanded = expanded,
        onDismissRequest = { onExpanded() },
    ) {
        IconButton(modifier = Modifier
            .align(End),
            onClick = { onExpanded() }) {
            Icon(Icons.Outlined.Close,"Close",)
        }
        DropdownMenuItem(
            text = { Text("Hard Mode") },
            onClick = { /* Handle edit! */ },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Edit,
                    contentDescription = null
                )
            })
        DropdownMenuItem(
            text = { Text("Settings") },
            onClick = { /* Handle settings! */ },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Settings,
                    contentDescription = null
                )
            })
        Divider()
        DropdownMenuItem(
            text = { Text("Send Feedback") },
            onClick = { /* Handle send feedback! */ },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Email,
                    contentDescription = null
                )
            },
            trailingIcon = { Text("F11", textAlign = TextAlign.Center) })
    }

*/

/*
    if (expanded) {
        AlertDialog(
            modifier = Modifier.fillMaxSize(),
            onDismissRequest = { onExpanded() }) {
            DropdownMenuItem(
                text = { Text("Edit") },
                onClick = { /* Handle edit! */ },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Edit,
                        contentDescription = null
                    )
                })
            DropdownMenuItem(
                text = { Text("Settings") },
                onClick = { /* Handle settings! */ },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Settings,
                        contentDescription = null
                    )
                })
        }
    }
 */
}