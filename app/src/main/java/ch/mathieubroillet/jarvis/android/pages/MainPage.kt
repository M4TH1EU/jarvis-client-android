package ch.mathieubroillet.jarvis.android.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ch.mathieubroillet.jarvis.android.R
import ch.mathieubroillet.jarvis.android.audio.AudioRecorder
import ch.mathieubroillet.jarvis.android.chat.ConversationUiState
import ch.mathieubroillet.jarvis.android.chat.Message
import ch.mathieubroillet.jarvis.android.chat.Messages
import ch.mathieubroillet.jarvis.android.nav.Screen
import ch.mathieubroillet.jarvis.android.ui.theme.productSansFont
import ch.mathieubroillet.jarvis.android.utils.DefaultBox
import ch.mathieubroillet.jarvis.android.utils.IconAlertDialogTextField
import ch.mathieubroillet.jarvis.android.utils.contactServerWithFileAudioRecording
import com.github.squti.androidwaverecorder.RecorderState
import org.json.JSONObject
import kotlin.concurrent.thread


//Draws the base of the main activity, that includes the 3-dots menu and the "hi text".
@Composable
fun Base(navController: NavController, uiState: ConversationUiState) {

    Column(
        Modifier
            .padding(bottom = 25.dp)
            .fillMaxWidth()
    ) {
        Row(Modifier.align(Alignment.End)) {
            var text by remember { mutableStateOf("") }

            IconAlertDialogTextField(
                buttonIcon = R.drawable.ic_baseline_keyboard_24,
                title = stringResource(id = R.string.main_page_dialog_ask_me_anything),
                label = stringResource(id = R.string.main_page_dialog_type_a_sentence),
                text = text,
                textFieldValue = { text = it },
                onOKClick = { uiState.addMessage(Message(false, text)); text = "" }
            )

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
        DropdownMenuItem(onClick = { navController.navigate(Screen.PermissionsScreen.route) }) {
            Text(stringResource(id = R.string.main_page_delete_conversation))
        }
        DropdownMenuItem(onClick = { navController.navigate(Screen.SettingsScreen.route) }) {
            Text(stringResource(id = R.string.settings))
        }
        Divider()
        DropdownMenuItem(onClick = { /* Handle send feedback! */ }) {
            Text(stringResource(id = R.string.report_an_issue))
        }
    }
}

@Composable
fun StartRecordingFAB(onClick: () -> Unit, isRecording: Boolean) {
    //We create a row that we align to the bottom center of the parent box
    Row(
        Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {

        //Microphone floating button to manually start/stop listening
        FloatingActionButton(onClick = onClick, modifier = Modifier.size(70.dp)) {
            Icon(
                painter = painterResource(id = if (isRecording) R.drawable.ic_baseline_shield_24 else R.drawable.ic_baseline_mic_24),
                contentDescription = "microphone"
            )
        }
    }
}


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

            Base(navController, uiState)

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
            var listening: Boolean by remember { mutableStateOf(false) }

            audioRecorder.waveRecorder.onStateChangeListener = {
                when (it) {
                    RecorderState.RECORDING -> listening = true
                    RecorderState.STOP -> {
                        listening = false

                        thread {
                            val requestOutput =
                                contactServerWithFileAudioRecording(audioRecorder.getOutputFile())

                            val json: JSONObject = JSONObject(requestOutput)
                            val sent = JSONObject(requestOutput).getString("sent").replace("\"", "")
                                .replace("[", "").replace("]", "").replace(",", " ")
                            sent.replaceFirstChar { sent.first().uppercase() }
                            uiState.addMessage(Message(false, sent))
                            Thread.sleep(1000)
                            uiState.addMessage(Message(true, json.getString("response")))
                            audioRecorder.getOutputFile().delete()
                        }
                    }
                    else -> {}
                }
            }

            StartRecordingFAB(
                onClick = { if (listening) audioRecorder.stopRecording() else audioRecorder.startRecording() },
                isRecording = listening
            )
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun MainPagePreview() {
    JarvisComposeTheme {
        DisplayMainPage(
            rememberNavController(), ConversationUiState(
                listOf(Message(true, stringResource(id = R.string.demo_message_1)))
            ), null
        )
    }
}*/