package ch.mathieubroillet.jarvis.android.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ch.mathieubroillet.jarvis.android.R
import ch.mathieubroillet.jarvis.android.ui.theme.productSansFont

@Composable
fun Messages(
    messages: List<Message>,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val reverse: List<Message> = messages.reversed()

            for (message in reverse) {
                item {
                    if (message.isJarvis) {
                        MessageFromJarvis(text = message.content)
                    } else {
                        MessageFromUser(text = message.content)
                    }
                }
            }
        }
    }
}

@Composable
fun MessageFromJarvis(
    text: String,
    anotherMessageFollows: Boolean = false,
    showProfileIcon: Boolean = true
) {
    //We create a row to contain the message and the robot image (to look like an sms)
    Row(
        Modifier.padding(
            bottom = if (anotherMessageFollows) 5.dp else 20.dp,
            start = if (!showProfileIcon) 50.dp else 0.dp
        )
    ) {

        // Adding the robot image as the sender
        if (showProfileIcon) {
            Image(
                painter = painterResource(id = R.drawable.robot256),
                contentDescription = "robot",
                Modifier
                    .size(50.dp)
                    .padding(end = 10.dp)
            )
        }

        // Adding the message box with the text given in the params
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.9F)
                .clip(RoundedCornerShape(15.dp))
                .background(color = MaterialTheme.colors.secondaryVariant)
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Text(text = text, fontFamily = productSansFont)
        }
    }
}

@Composable
fun MessageFromJarvis(texts: List<String>) {
    //Automated function to send multiples messages at the same time but only showing the profile pic in the first one and reducing the padding between them.
    var i = texts.size
    for (text in texts) {
        when (i) {
            texts.size -> MessageFromJarvis(
                text = text,
                anotherMessageFollows = true,
                showProfileIcon = true
            )
            1 -> MessageFromJarvis(
                text = text,
                anotherMessageFollows = false,
                showProfileIcon = false
            )
            else -> MessageFromJarvis(
                text = text,
                anotherMessageFollows = true,
                showProfileIcon = false
            )
        }
        i--
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
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Text(
                text = text,
                fontFamily = productSansFont,
                color = if (!isSystemInDarkTheme()) Color.White else Color(15, 15, 15, 255)
            )
        }
    }
}
