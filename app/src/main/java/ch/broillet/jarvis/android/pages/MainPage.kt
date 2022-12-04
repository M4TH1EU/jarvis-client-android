package ch.broillet.jarvis.android.pages

import android.provider.Settings.Secure
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ch.broillet.jarvis.android.R
import ch.broillet.jarvis.android.audio.AudioRecorder
import ch.broillet.jarvis.android.chat.ConversationUiState
import ch.broillet.jarvis.android.chat.Message
import ch.broillet.jarvis.android.chat.Messages
import ch.broillet.jarvis.android.nav.Screen
import ch.broillet.jarvis.android.ui.theme.JarvisclientappTheme
import ch.broillet.jarvis.android.ui.theme.productSansFont
import ch.broillet.jarvis.android.utils.*
import com.github.squti.androidwaverecorder.RecorderState
import com.github.squti.androidwaverecorder.WaveRecorder
import org.json.JSONObject
import java.io.File
import kotlin.concurrent.thread


@Composable
fun DisplayMainPage(
    navController: NavController,
    uiState: ConversationUiState,
    audioRecorder: AudioRecorder
) {

    //We create a main box with basic padding to avoid having stuff too close to every side.
    DefaultBox {

        // This column regroup the base and all the conversations (everything except the footer)
        Column(Modifier.padding(bottom = 80.dp)) {

            MainBase(navController)

            Messages(
                messages = uiState.messages,
                modifier = Modifier.weight(1f)
            )
        }

        // Finally we add the footer to the bottom center of the main box
        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
        ) {

            RecordingFooterButton(
                audioRecorder = audioRecorder,
                navController = navController,
                uiState = uiState
            )
        }
    }
}

//Draws the base of the main activity, that includes the 3-dots menu and the "hi text".
@Composable
fun MainBase(navController: NavController) {

    Column(
        Modifier
            .padding(bottom = 25.dp)
            .fillMaxWidth()
    ) {
        Row(Modifier.align(Alignment.End)) {
            DropDownSettingsMenu(navController)
        }

        Text(
            text = stringResource(id = R.string.main_page_how_may_i_help_you),
            fontFamily = productSansFont,
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 30.dp)
        )
    }
}

@Composable
fun DropDownSettingsMenu(navController: NavController) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
            contentDescription = "3 dots button"
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        offset = DpOffset((-500).dp, 0.dp)
    ) {
        DropdownMenuItem(
            text = { Text(stringResource(id = R.string.main_page_delete_conversation)) },
            onClick = { navController.navigate(Screen.PermissionsScreen.route) },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Edit,
                    contentDescription = null
                )
            })
        DropdownMenuItem(
            text = { Text(stringResource(id = R.string.settings)) },
            onClick = { navController.navigate(Screen.SettingsScreen.route) },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Settings,
                    contentDescription = null
                )
            })
        DropdownMenuItem(
            text = { Text(stringResource(id = R.string.report_an_issue)) },
            onClick = { /* Handle send feedback! */ },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Share,
                    contentDescription = null
                )
            })
    }
}

@Composable
fun RecordingFooterButton(
    audioRecorder: AudioRecorder,
    navController: NavController,
    uiState: ConversationUiState
) {

    var isListening: Boolean by remember { mutableStateOf(false) }
    var isProcessing: Boolean by remember { mutableStateOf(false) }

    // Add a listener for the waveRecorder to record when isListening is true and then process the audio when done listening
    audioRecorder.waveRecorder.onStateChangeListener = {
        when (it) {
            RecorderState.RECORDING -> isListening = true
            RecorderState.STOP -> {
                thread {
                    isListening = false
                    isProcessing = true

                    processMessage(
                        processAudio(audioRecorder.getOutputFile()),
                        navController,
                        uiState
                    )

                    isProcessing = false
                    audioRecorder.getOutputFile().delete()
                }
            }
            else -> {}
        }
    }

    //We create a row that we align to the bottom center of the parent box
    Row(
        Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {

        //Microphone floating button to manually start/stop listening
        FloatingActionButton(
            onClick = { if (isListening) audioRecorder.stopRecording() else audioRecorder.startRecording() },
            modifier = Modifier.size(70.dp)
        ) {
            if (isListening) {
                DotsTyping(7.dp, 3, 300, MaterialTheme.colorScheme.secondary, 2.dp)
            } else {
                if (isProcessing) {
                    DotsFlashing(7.dp, 3, 300, MaterialTheme.colorScheme.secondary, 2.dp)
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_mic_24),
                        contentDescription = "microphone"
                    )
                }
            }
        }
    }
}

fun processAudio(audioFile: File): String {
    val json = JSONObject(getTextFromAudio(audioFile))

    return json.getString("data")
}

fun processMessage(text: String, navController: NavController, uiState: ConversationUiState) {
    navController.context.mainExecutor.execute {
        SocketHandler.processMessage(
            text,
            Secure.getString(
                navController.context.contentResolver,
                Secure.ANDROID_ID
            )
        )
    }

    uiState.addMessage(Message(false, text))
}


@Preview(showBackground = true)
@Composable
fun MainPagePreview() {
    JarvisclientappTheme {
        DisplayMainPage(
            rememberNavController(), ConversationUiState(
                listOf(Message(true, stringResource(id = R.string.demo_message_1)))
            ), audioRecorder = AudioRecorder("", WaveRecorder(""))
        )
    }
}