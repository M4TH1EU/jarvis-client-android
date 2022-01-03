package ch.mathieubroillet.jarvis.android.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ch.mathieubroillet.jarvis.android.R
import ch.mathieubroillet.jarvis.android.nav.Screen
import ch.mathieubroillet.jarvis.android.ui.theme.JarvisComposeTheme
import ch.mathieubroillet.jarvis.android.ui.theme.productSansFont
import ch.mathieubroillet.jarvis.android.utils.DefaultBox
import ch.mathieubroillet.jarvis.android.utils.IconAlertDialogTextField
import ch.mathieubroillet.jarvis.android.utils.MessageFromJarvis
import ch.mathieubroillet.jarvis.android.utils.MessageFromUser


//Draws the base of the main activity, that includes the 3-dots menu and the "hi text".
@Composable
fun Base(navController: NavController) {

    Column(Modifier.padding(bottom = 25.dp)) {
        Row(Modifier.align(Alignment.End)) {

            IconAlertDialogTextField(
                R.drawable.ic_baseline_keyboard_24,
                "Demandez-moi quelque chose",
                "Entrez une phrase"
            )

            DropDownSettingsMenu(navController)
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
        DropdownMenuItem(onClick = { /* Handle refresh! */ }) {
            Text("Effacer la conversation")
        }
        DropdownMenuItem(onClick = { navController.navigate(Screen.SettingsScreen.route) }) {
            Text("Paramètres")
        }
        Divider()
        DropdownMenuItem(onClick = { /* Handle send feedback! */ }) {
            Text("Signaler un problème")
        }
    }
}

@Composable
fun StartRecordingFAB() {
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
fun DisplayMainPage(navController: NavController) {
    //We create a main box with basic padding to avoid having stuff too close to every side.
    DefaultBox {

        // This column regroup the base and all the conversations (everything except the footer)
        Column(Modifier.padding(bottom = 80.dp)) {
            Base(navController)

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
            StartRecordingFAB()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainPagePreview() {
    JarvisComposeTheme {
        DisplayMainPage(rememberNavController())
    }
}