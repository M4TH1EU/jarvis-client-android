package ch.mathieubroillet.jarvis.android

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import ch.mathieubroillet.jarvis.android.ui.theme.JarvisComposeTheme
import ch.mathieubroillet.jarvis.android.ui.theme.productSansFont
import ch.mathieubroillet.jarvis.android.utils.IconAlertDialogTextField

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContent {
            // Fix the status & navigation bars staying white when white theme is on with activity in fullscreen
            val wic = WindowInsetsControllerCompat(window, window.decorView)
            wic.isAppearanceLightStatusBars = !isSystemInDarkTheme()
            wic.isAppearanceLightNavigationBars = !isSystemInDarkTheme()

            JarvisComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background, modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    //TODO: check if this is okay???
                    DefaultPreview()
                }
            }
        }
    }
}

var messageScroll: ScrollState? = null

//Draws the base of the main activity, that includes the 3-dots menu and the "hi text".
@Composable
fun Base() {
    var expanded by remember { mutableStateOf(false) }

    Column(Modifier.padding(bottom = 25.dp)) {
        Row(Modifier.align(Alignment.End)) {

            IconAlertDialogTextField(
                R.drawable.ic_baseline_keyboard_24,
                "Demandez-moi quelque chose",
                "Entrez une phrase"
            )

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
                DropdownMenuItem(onClick = { /* Handle refresh! */ }) {
                    Text("Effacer la conversation")
                }
                DropdownMenuItem(onClick = { /* Handle settings! */ }) {
                    Text("Paramètres")
                }
                Divider()
                DropdownMenuItem(onClick = { /* Handle send feedback! */ }) {
                    Text("Signaler un problème")
                }
            }
        }

        Text(
            text = "Bonjour, comment puis-je vous aider ?",
            fontFamily = productSansFont,
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 30.dp)
        )
    }
}

@Composable
fun RecordFloatingButton() {
    //We create a row that we align to the bottom center of the parent box
    Row(
        Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        //Microphone floating button to manually start/stop listening
        FloatingActionButton(onClick = { /*TODO*/ }, modifier = Modifier.size(70.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_mic_24),
                contentDescription = "microphone"
            )
        }
    }
}

@Composable
fun MessageFromJarvis(text: String) {
    //We create a row to contain the message and the robot image (to look like an sms)
    Row(Modifier.padding(bottom = 25.dp)) {

        // Adding the robot image as the sender
        Image(
            painter = painterResource(id = R.drawable.robot256),
            contentDescription = "robot",
            Modifier
                .size(50.dp)
                .padding(end = 10.dp)
        )

        // Adding the message box with the text given in the params
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.9F)
                .clip(RoundedCornerShape(15.dp))
                .background(color = MaterialTheme.colors.secondaryVariant)
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Text(text = text, fontFamily = productSansFont)
        }
    }
}

@Composable
fun MessageFromUser(text: String) {
    //We create a row to contain the user message and we align the row to the right side (to look like a conversation between two people)
    Row(
        Modifier
            .padding(bottom = 25.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.End
    ) {
        // The message box with the text
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.8F)
                .clip(RoundedCornerShape(15.dp))
                .background(color = MaterialTheme.colors.secondary)
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Text(
                text = text,
                fontFamily = productSansFont,
                color = if (!isSystemInDarkTheme()) Color.White else Color(15, 15, 15, 255)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JarvisComposeTheme {
        //We create a main box with basic padding to avoid having stuff too close to every side.
        Box(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .padding(top = 45.dp, bottom = 10.dp)
        ) {

            // This column regroup the base and all the conversations (everything except the footer)
            Column(Modifier.padding(bottom = 80.dp)) {
                Base()

                messageScroll = rememberScrollState()
                // This column regroup only the conversations and make them scrollable

                LazyColumn(content = {
                    item {
                        // Basic interaction stuff for demo
                        MessageFromJarvis(text = "Salut, je suis Jarvis! \nPose moi une question et je ferais de mon mieux pour te renseigner.")
                        MessageFromUser(text = "Quel temps fait-il à Paris en ce moment ?")
                        MessageFromJarvis(text = "A Paris, il fait actuellement 10 degrés et le ciel est nuageux.")
                    }
                })
            }


            // Finally we add the footer to the bottom center of the main box
            Column(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 40.dp)
            ) {
                RecordFloatingButton()
            }
        }
    }
}